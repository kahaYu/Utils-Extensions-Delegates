// The view model’s event stream is defined using a channel receiving as a flow. 
// This allows the view model to submit events without having to know about the state of the observer. 
// Events are buffered when there is no observer.

class MainViewModel : ViewModel() {

    sealed class Event {
        object NavigateToSettings: Event()
        data class ShowSnackBar(val text: String): Event()
        data class ShowToast(val text: String): Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    
    // Wherever we want to send single live event, we should create coroutine and execute .send on eventChannel.
        viewModelScope.launch {
            eventChannel.send(Event.ShowSnackBar("Sample"))
            eventChannel.send(Event.ShowToast("Toast"))
        }


// Collection of the events in Activity.
class LocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a new coroutine since repeatOnLifecycle is a suspend function
        lifecycleScope.launch {
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from locationFlow when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                locationProvider.locationFlow().collect {
                    // New location! Update the map
                }
            }
        }
    }
}

// Collection of the events in Fragments.
class LocationFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ...
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                locationProvider.locationFlow().collect {
                    // New location! Update the map
                }
            }
        }
    }
}

// Or we can create extentions to reduce boilerplate code.

inline fun <reified T> Flow<T>.observeWithLifecycle(
        lifecycleOwner: LifecycleOwner,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        noinline action: suspend (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
}
// OR
inline fun <reified T> Flow<T>.observeWithLifecycle(
        fragment: Fragment,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        noinline action: suspend (T) -> Unit
): Job = fragment.viewLifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle, minActiveState).collect(action)
}

// Usage of extentions.

viewModel.events
    .observeWithLifecycle(fragment = this, minActiveState = Lifecycle.State.RESUMED) {
        // do things
    }
//OR
viewModel.events
    .observeWithLifecycle(lifecycleOwner = viewLifecycleOwner, minActiveState = Lifecycle.State.RESUMED) {
        // do things
    }
