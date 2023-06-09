package com.tva.cashcoach.infrastructure.base

/**
 * Base Controller Functions used in activities, fragments, dialogs, bottomsheet
 */
interface BaseControllerFunctionsImpl {

    /**
     * All initialization related work will be done in this method.
     * i.e. Handling lifecycle methods.
     */
    fun onInitialized() {}

    /**
     * All observer listener code will be handled in this method inside controllers.
     */
    fun addObservers() {}

    /**
     * All click action code will be handled in this method inside controllers.
     */
    fun setUpClicks()

}