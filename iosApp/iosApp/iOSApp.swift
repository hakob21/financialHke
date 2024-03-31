import SwiftUI
import ComposeApp // pay attention to the uppercase in ComposeApp. In reality composeApp is a package with lowercase

@main
struct iOSApp: App {
    init() {
        HelperKt.doInitKoin()
    }
 	var body: some Scene {
 		WindowGroup {
 			ContentView()
 		}
 	}
}