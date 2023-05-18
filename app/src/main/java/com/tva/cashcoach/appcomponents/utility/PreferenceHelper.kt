package com.tva.cashcoach.appcomponents.utility

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

/**
 * class which used to manage application shared preference
 */
class PreferenceHelper {
    private val masterKeyAlias: String = createGetMasterKey()

    private val sharedPreference: SharedPreferences = EncryptedSharedPreferences.create(
        MyApp.getInstance().resources.getString(R.string.app_name),
        masterKeyAlias,
        MyApp.getInstance().applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * Creates or gets the master key provided,
     * The encryption scheme is required fields to ensure that the type of encryption used is clear to developers.
     *2
     * @return the string value of encrypted key
     */
    private fun createGetMasterKey(): String {
        return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    /**
     * Put a string value in the shared preferences.
     *
     * @param key the key to store the value under
     * @param value the value to store
     */
    fun putString(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    /**
     * Get a string value from the shared preferences.
     *
     * @param key the key to retrieve the value for
     * @param defaultValue the default value to return if the key is not found
     * @return the string value for the key, or the default value if the key is not found
     */
    fun getString(key: String, defaultValue: String): String {
        return sharedPreference.getString(key, defaultValue) ?: defaultValue
    }

    /**
     * Put an integer value in the shared preferences.
     *
     * @param key the key to store the value under
     * @param value the value to store
     */
    fun putInt(key: String, value: Int) {
        sharedPreference.edit().putInt(key, value).apply()
    }

    /**
     * Get an integer value from the shared preferences.
     *
     * @param key the key to retrieve the value for
     * @param defaultValue the default value to return if the key is not found
     * @return the integer value for the key, or the default value if the key is not found
     */
    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreference.getInt(key, defaultValue)
    }

    /**
     * Put a boolean value in the shared preferences.
     *
     * @param key the key to store the value under
     * @param value the value to store
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreference.getBoolean(key, defaultValue)
    }

    /**
     * Put a boolean value in the shared preferences.
     *
     * @param key the key to store the value under
     * @param value the value to store
     */
    fun putBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    /**
     * Remove a value from the shared preferences.
     * @param key the key to remove the value for
     */
    fun removeValue(key: String) {
        sharedPreference.edit().remove(key).apply()
    }

    /**
     * Remove all values from the shared preferences.
     */
    fun removeAllValues() {
        sharedPreference.edit().clear().apply()
    }
}