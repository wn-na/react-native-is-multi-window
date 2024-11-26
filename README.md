# react-native-is-multi-window

Quickly and easily check if your app is multiwindow

## Support Platform
 
<table style="border: 2px;">
  <tr>
    <td>Android</td>
    <td>iOS, iPad OS</td> 
  </tr><tr>
    <td>
      <img src="https://github.com/user-attachments/assets/945aac7c-c5a5-42dd-8e50-5527c0ea77d4" width="480px" />
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/fa7b74bf-3fd4-4d36-a929-d18436a3ae48" width="480px" />
    </td>
  </tr>
</table>

## Installation

```sh
npm install react-native-is-multi-window
```

## Configuration

### Android

Add following to `MainActivity.java` or `MainActivity.kt`

#### `MainActivity.java`

```java
import android.content.Intent;


  @Override
  public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
      super.onMultiWindowModeChanged(isInMultiWindowMode);
      Intent intent = new Intent("onMultiWindowModeChanged");
      intent.putExtra("isInMultiWindowMode", isInMultiWindowMode);
      this.sendBroadcast(intent);
  }
```

#### `MainActivity.kt`

```kotlin
import android.content.Intent


  override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
    super.onMultiWindowModeChanged(isInMultiWindowMode)
    val intent =
            Intent("onMultiWindowModeChanged").apply {
              putExtra("isInMultiWindowMode", isInMultiWindowMode)
            }
    sendBroadcast(intent)
  }
```

## Usage

### Without Hook

```js
import { isMultiWindowMode } from 'react-native-is-multi-window';

// ...

const result = await isMultiWindowMode();
```

### With Hook

```js
import { useMultiWindowMode } from 'react-native-is-multi-window';

// ...

const { isMultiMode } = useMultiWindowMode();

useEffect(() => {
  console.log(`MultiMode => ${isMultiMode}`);
}, [isMultiMode]);
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
