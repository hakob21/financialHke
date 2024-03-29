import android.app.Application
import android.content.Context
import android.os.Build
import shared.DriverFactory

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()