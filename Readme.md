# Test App

## Descripción

Esta aplicación de Test tiene como objetivo probar la integración con Google Talkback y cómo utilizarlo desde el código.

## Características Clave

1. **Navegación Transversal**:
    - Los usuarios pueden navegar de forma transversal de izquierda a derecha a través de los elementos interactivos en la pantalla.

2. **Navegación por Arrastre**:
    - Los usuarios también tienen la opción de navegar arrastrando el dedo a través de la pantalla para explorar los elementos interactivos.

3. **Acciones Personalizadas**:
    - Se han definido acciones personalizadas mediante la API de accesibilidad de Android, permitiendo a los usuarios interactuar con la aplicación de manera más eficaz.

Google Talkback leerá en voz alta todos los elementos en los que ponga el foco. Podemos indicar cómo queremos que Talkback interactúe con los elementos del XML.

### Configuraciones de Accesibilidad en XML

- `android:importantForAccessibility`:
    - `no`: Este elemento no es importante para la accesibilidad, por lo que TalkBack lo ignorará.
    - `yes`: Este elemento es importante para la accesibilidad, por lo que TalkBack lo incluirá en su navegación.
    - `auto`: El sistema decide si el elemento es importante para la accesibilidad o no, generalmente basado en si el elemento es interactivo o no.

- `android:accessibilityLiveRegion`:
    - `none`: No se anunciarán cambios en este elemento.
    - `polite`: Los cambios se anunciarán al final de las actuales interacciones de accesibilidad en curso.
    - `assertive`: Los cambios se anunciarán inmediatamente, interrumpiendo cualquier otra interacción de accesibilidad en curso.

### Anunciar Texto con TalkBack

Para hacer que Talkback diga en voz alta algo, se utiliza el siguiente código:

```kotlin
binding.recyclerView.announceForAccessibility("Texto en voz alta")
```

Esto permite hacer que TalkBack hable cuando se llame a esta función.

###  Definición de Acciones Personalizadas
Aquí hay un ejemplo de cómo definir una acción personalizada para TalkBack:

```kotlin
val myAccessibilityDelegate = object : AccessibilityDelegateCompat() {

    override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(host, info)
        val actionLabel = "Texto que describe la acción"
        val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
            AccessibilityNodeInfoCompat.ACTION_CLICK, actionLabel
        )
        info.addAction(customAction)
    }

    override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
        if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
            binding.title.requestFocus()
            binding.title.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            return true
        }
        return super.performAccessibilityAction(host, action, args)
    }
}
ViewCompat.setAccessibilityDelegate(binding.titleRecyclerView, myAccessibilityDelegate)
```

Donde  ```onInitializeAccessibilityNodeInfo```Defines la acción y el texto en voz alta de la acción y en ```performAccessibilityAction``` lo que quieres que pase.

### Dirigir el foco de Talkback
```kotlin
binding.title.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
``` 
Estamos indicando que el foco de Google TalkBack se mueva hacia binding.title.