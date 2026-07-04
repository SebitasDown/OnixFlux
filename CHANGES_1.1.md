# CHANGES 1.1 — OnixFlux FX Engine Core

> Archivo de contexto para IA y desarrolladores.
> Describe la arquitectura completa del núcleo del sistema FX (Flux Engine)
> construido sobre Minecraft Forge 1.20.1 (Forge 47.4.10), Java 17.

---

## 1. PROPÓSITO

OnixFlux es un mod de transmutación. Su núcleo es el **FX Engine**: un sistema
que asigna valores de equivalencia de materia (`long`) a cada `Item` de
Minecraft. En el futuro, los jugadores podrán convertir objetos en FX y
recrearlos mediante una Flux Table.

**Filosofía:** `SOLID`, Clean Code, inmutabilidad, cero deuda técnica.

---

## 2. ESTRUCTURA DEL PACKAGE

```
com.sebitas.onixflux
├── OnixFlux.java                  # @Mod — punto de entrada, wiring de eventos Forge
└── fx/
    ├── FluxEngine.java            # API pública estática (punto de acceso único)
    ├── FluxRegistry.java          # Almacenamiento interno (package-private)
    ├── FluxValue.java             # Record inmutable (value, source)
    ├── FluxSource.java            # Enum con prioridades (DEFAULT < CALCULATED < API < CONFIG)
    ├── FluxBootstrap.java         # Orquestador de inicialización en 6 fases
    ├── FluxCalculator.java        # Resolución iterativa de valores vía recetas
    ├── FluxCycleDetector.java     # Detección de ciclos (Tarjan SCC)
    ├── FluxLoader.java            # Carga de configuración TOML (Forge config system)
    └── FluxException.java         # RuntimeException personalizada
```

**Clases eliminadas:**
- `FluxRecipeGraph.java` — no especificada en el diseño original, su funcionalidad
  fue absorbida por `FluxCalculator` y `FluxCycleDetector` (grafos internos
  con `Map<Item, Set<Item>>`).

---

## 3. DIAGRAMA DE DEPENDENCIAS

```
OnixFlux (@Mod)
  │
  ├── llama a FluxBootstrap.bootstrap()        [constructor]
  ├── llama a FluxBootstrap.loadConfig()        [FMLCommonSetupEvent]
  └── llama a FluxBootstrap.finalize(recipes)   [ServerAboutToStartEvent]
        │
        ├── FluxCalculator.calculate(registry, recipes, registryAccess)
        ├── FluxCycleDetector.detectAndRemove(registry, recipes, registryAccess)
        └── FluxEngine.freeze()
              │
              └── FluxRegistry (package-private, solo consultas después de freeze)
```

---

## 4. CLASES — RESPONSABILIDAD Y DISEÑO

### 4.1 FluxSource (enum)
- **Propósito:** Tipificar el origen de cada valor FX.
- **Prioridades (mayor = más autoridad):**
  - `DEFAULT(0)` — valores vanilla predefinidos
  - `CALCULATED(1)` — derivados de recetas
  - `API(2)` — registrados por otros mods
  - `CONFIG(3)` — definidos por el usuario en TOML
- **Uso:** `FluxRegistry.register()` solo sobreescribe si `new.source.priority() >= existing.source.priority()`.

### 4.2 FluxValue (record)
- **Propósito:** Tupla inmutable `(long value, FluxSource source)`.
- **Validación:** Lanza `IllegalArgumentException` si `value < 0`.
- **Métodos:** `isCalculated()` para filtrar valores derivados.

### 4.3 FluxException (class)
- **Propósito:** Excepción unchecked del sistema. Se lanza al intentar modificar el engine congelado.

### 4.4 FluxRegistry (class — package-private)
- **Propósito:** Único punto de almacenamiento. Sin lógica de cálculo.
- **Métodos clave:**
  - `register(item, value)` — con prioridad (no sobreescribe si existe con igual o mayor prioridad)
  - `forceRegister(item, value)` — sin verificación de prioridad (usado por FluxCalculator)
  - `get(item)` → `Optional<FluxValue>`
  - `getAll()` → `Map.copyOf(values)` — copia inmutable, nunca expone el mapa interno
  - `removeCalculated()` — limpia valores derivados (usado en recarga)
  - `removeAll(Set<Item>)` — eliminación por lote (usado por FluxCycleDetector)

### 4.5 FluxEngine (class — pública, estática)
- **Propósito:** Fachada pública del sistema. Es el **único** punto de acceso.
- **State machine:** `frozen = false` durante bootstrap → `frozen = true` tras finalize.
- **API pública (6 métodos):**
  - `register(Item, long, FluxSource)` — lanza excepción si frozen
  - `getValue(Item)` → `Optional<FluxValue>`
  - `hasValue(Item)` → `boolean`
  - `remove(Item)` — lanza excepción si frozen
  - `clear()` — lanza excepción si frozen
  - `reload()` — limpia todo, re-ejecuta bootstrap + loadConfig, deja unfrozen
- **API interna (package-private):**
  - `registry()` — acceso al `FluxRegistry` para clases del paquete fx
  - `freeze()` — llamado por `FluxBootstrap`
  - `logger()` — acceso al Logger SLF4J

### 4.6 FluxCalculator (class — package-private)
- **Propósito:** Derivar valores FX automáticamente desde recetas de Minecraft.
- **Algoritmo:** Resolución iterativa (do-while).
  1. Por cada receta: si todos los ingredientes tienen valor, calcular output.
  2. `outputValue = sum(ingredientValues) / outputCount` (división entera, trunca).
  3. Usa `forceRegister` para asignar valores `CALCULATED`.
  4. Repite hasta que ningún nuevo valor sea descubierto.
- **Tipos de receta soportados:** CRAFTING, SMELTING, BLASTING, SMOKING, CAMPFIRE_COOKING.
- **Manejo de ingredientes ambiguos:** Usa el primer `ItemStack` de `Ingredient.getItems()`.

### 4.7 FluxCycleDetector (class — package-private)
- **Propósito:** Detectar y eliminar items involucrados en ciclos de recetas (A→B→C→A).
- **Algoritmo:** **Tarjan's SCC** (Strongly Connected Components).
  1. Construye grafo dirigido: `output → ingredient` (output depende de ingredient).
  2. Encuentra SCCs con tamaño > 1 (esos son ciclos).
  3. Elimina todos los items cíclicos del registry vía `removeAll()`.
- **Por qué Tarjan:** O(V+E), single-pass, detecta todos los ciclos correctamente.

### 4.8 FluxLoader (class — pública)
- **Propósito:** Cargar valores desde configuración externa.
- **Config:** Usa `ForgeConfigSpec` (TOML) con lista de strings `"modid:item_path=value"`.
- **Métodos:**
  - `configSpec()` → devuelve el `ForgeConfigSpec` para registrar en `ModLoadingContext`.
  - `loadCustomValues()` → parsea la config y registra con `FluxSource.CONFIG`.

### 4.9 FluxBootstrap (class — pública)
- **Propósito:** Orquestar las 6 fases de inicialización en el orden correcto.
- **Fases:**
  1. `registerVanillaDefaults()` — valores base (piedra=1, diamante=8192, etc.)
  2. `FluxLoader.loadCustomValues()` — valores de config TOML
  3. *(Implícita)* — otros mods registran valores API durante su setup
  4. `FluxCalculator.calculate()` — deriva valores de recetas
  5. `FluxCycleDetector.detectAndRemove()` — elimina cíclicos
  6. `FluxEngine.freeze()` — congela el sistema
- **Métodos:**
  - `bootstrap()` — fase 1 (constructor del mod)
  - `loadConfig()` — fase 2 (FMLCommonSetupEvent)
  - `finalize(RecipeManager, RegistryAccess)` — fases 4-6 (ServerAboutToStartEvent)
  - `reset()` — para recarga (package-private)

---

## 5. REGISTRO DE VALORES VANILLA (DEFAULT)

| Item | Valor FX |
|------|----------|
| Stone, Cobblestone, Dirt, Gravel, Sand | 1 |
| End Stone | 1 |
| Cobblestone Wall | 1 |
| Stick | 4 |
| Flint | 4 |
| Oak/Spruce/Birch/Jungle/Acacia/Dark Oak/Mangrove Planks | 8 |
| Clay Ball | 16 |
| Brick | 16 |
| Nether Brick | 16 |
| Oak Log | 32 |
| Redstone | 64 |
| Coal, Charcoal | 128 |
| Iron Ingot | 256 |
| Quartz, Prismarine Shard | 256 |
| Iron Nugget | 28 |
| Lapis Lazuli | 864 |
| Prismarine Crystals | 1024 |
| Gold Ingot | 2048 |
| Gold Nugget | 227 |
| Obsidian | 4096 |
| Diamond | 8192 |
| Emerald | 16384 |
| Netherite Ingot | 139264 |

> Valores inspirados en ProjectE/Equivalent Exchange, adaptados para coherencia
> con las recetas de vanilla Minecraft 1.20.1.

---

## 6. CICLO DE VIDA EN FORGE

```
1. Mod Constructor (@Mod)
   ├── registerConfig(COMMON, FluxLoader.configSpec())
   └── FluxBootstrap.bootstrap()
        └── Fase 1: valores DEFAULT registrados

2. FMLCommonSetupEvent
   └── FluxBootstrap.loadConfig()
        └── Fase 2: valores CONFIG registrados (sobrescriben DEFAULT por prioridad)
             └── (Fase 3 implícita: valores API de otros mods ya registrados)

3. ServerAboutToStartEvent
   └── FluxBootstrap.finalize(server.getRecipeManager(), server.registryAccess())
        ├── Fase 4: FluxCalculator → valores CALCULATED
        ├── Fase 5: FluxCycleDetector → elimina cíclicos
        └── Fase 6: FluxEngine.freeze() → sistema congelado

4. Gameplay
   └── Solo consultas (getValue, hasValue)
        └── Cualquier intento de registro lanza FluxException
```

---

## 7. CONFIGURACIÓN (TOML)

Archivo generado automáticamente por Forge en `config/onixflux-common.toml`.

```toml
# Custom FX values for items.
# Format: "modid:item_path=value"
# Example: "minecraft:diamond=16384"
custom_values = ["minecraft:diamond=16384", "minecraft:netherite_ingot=278528"]
```

Se accede desde código vía `FluxLoader.loadCustomValues()`.

---

## 8. API PÚBLICA PARA OTROS MODS

Cualquier mod puede registrar valores FX durante su inicialización:

```java
// En FMLCommonSetupEvent o constructor del mod:
FluxEngine.register(Items.DIAMOND, 8192L, FluxSource.API);
```

La prioridad `API(2)` sobrescribe `DEFAULT(0)` pero no `CONFIG(3)`.

---

## 9. DETECCIÓN DE CICLOS (EJEMPLO)

```
Recetas:
  A + B → C    (C depende de A, B)
  C + D → A    (A depende de C, D)

El detector construye el grafo:
  C → {A, B}
  A → {C, D}

Tarjan encuentra SCC = {A, C} con tamaño > 1 → CICLO.
  A y C son eliminados del registry (sin valor FX).
```

---

## 10. PRÓXIMOS PASOS (v1.2+)

- [ ] **Flux Table** — bloque GUI que consulta `FluxEngine.getValue()`
- [ ] **Capabilities del jugador** — `AttachCapabilitiesEvent` para FX por jugador
- [ ] **Sistema de objetos aprendidos** — qué items ha descubierto el jugador
- [ ] **API pública dedicada** — interfaz `IFluxAPI` para otros mods
- [ ] **Compatibilidad con mods** — registro automático de items de mods populares
- [ ] **Comandos** — `/onixflux reload`, `/onixflux query <item>`
- [ ] **Sonidos y partículas** — al transmutar items

---

## 11. NOTAS TÉCNICAS

- **Java 17** — se usan records, pattern matching instanceof, `List.of()`, `Map.copyOf()`.
- **Official mappings** — `mapping_channel=official`, `mapping_version=1.20.1`.
- **Logger** — SLF4J vía `LoggerFactory.getLogger("FluxEngine")`.
- **ForgeConfigSpec** — sistema de configuración estándar de Forge (TOML).
- **RecipeManager** — `getAllRecipesFor()` (no `byType()` que es private en official mappings).
- **RegistryAccess** — necesario para `Recipe.getResultItem(RegistryAccess)` desde 1.20.

---

*Documento generado para facilitar la continuidad del desarrollo por parte de
agentes IA o desarrolladores humanos.*
