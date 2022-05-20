package com.technoecorp.gorilladealer.utils

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class Validator {
    companion object {
        private fun isEmpty(text: String): Boolean {
            return text.isEmpty()
        }

        private fun isEqual(text: String, other: String): Boolean {
            return !text.lowercase().equals(other, false)
        }


        private fun checkLength(text: String, length: Int): Boolean {
            return text.length != length
        }

        private fun <T> checkDefaultValue(text: T, default: T): Boolean {
            return text.toString().equals(default.toString(), false)
        }

        private fun validMobilePattern(phone: String): Boolean {
            return !Patterns.PHONE.matcher(phone).matches()
        }

        private fun validEmailPattern(phone: String): Boolean {
            return !Patterns.EMAIL_ADDRESS.matcher(phone).matches()
        }

        fun validMobileNumber(mobile: TextInputEditText): Boolean {
            val text = mobile.text.toString()
            when {
                isEmpty(text) -> {
                    mobile.error = "Mobile Number can't be empty"
                    return false
                }
                checkLength(text, 10) -> {
                    mobile.error = "Enter At least 10 Digits"
                    return false
                }
                validMobilePattern(text) -> {
                    mobile.error = "Enter At Valid Mobile Number"
                    return false
                }
                else -> {
                    mobile.error = null
                    return true
                }
            }
        }

        fun validEmailAddress(email: TextInputEditText): Boolean {
            val text = email.text.toString()
            when {
                isEmpty(text) -> {
                    email.error = "Email Address can't be empty"
                    return false
                }
                validEmailPattern(text) -> {
                    email.error = "Enter At Valid Email Address"
                    return false
                }
                else -> {
                    email.error = null
                    return true
                }
            }
        }

        fun validCodeWithOutLength(code: TextInputEditText): Boolean {
            val text = code.text.toString()
            when {
                isEmpty(text) -> {
                    code.error = "Code can't be empty"
                    return false
                }
                else -> {
                    code.error = null
                    return true
                }
            }
        }

        fun validOtpCodeWithLength(code: TextInputEditText, length: Int): Boolean {
            val text = code.text.toString()
            when {
                isEmpty(text) -> {
                    code.error = "Code can't be empty"
                    return false
                }
                checkLength(text, length) -> {
                    code.error = "Enter At least $length Digits"
                    return false
                }
                else -> {
                    code.error = null
                    return true
                }
            }
        }

        fun validTextField(textField: TextInputEditText, fieldName: String): Boolean {
            val text = textField.text.toString()
            when {
                isEmpty(text) -> {
                    textField.error = "$fieldName can't be empty"
                    return false
                }
                else -> {
                    textField.error = null
                    return true
                }
            }
        }

        fun matchTextField(
            textField: TextInputEditText,
            textFieldOther: TextInputEditText,
            fieldName: String
        ): Boolean {
            val text = textField.text.toString()
            val other = textFieldOther.text.toString()
            when {
                isEqual(text, other) -> {
                    textField.error = "$fieldName should match"
                    textFieldOther.error = "$fieldName should match"
                    return false
                }
                else -> {
                    textField.error = null
                    textFieldOther.error = null
                    return true
                }
            }
        }


        fun validDateField(textField: TextInputEditText, fieldName: String): Boolean {
            val text = textField.text.toString()
            return when {
                isEmpty(text) -> {
                    textField.error = "$fieldName can't be empty"
                    false
                }
                !checkDateFormat(text) -> {
                    textField.error = "$fieldName should be in dd/MM/yyyy format"
                    false
                }
                else -> {
                    textField.error = null
                    true
                }
            }
        }

        fun <T> validDropDownSelection(
            textField: AutoCompleteTextView,
            fieldName: String,
            fieldValue:T,
            defaultValue: T
        ): Boolean {
            val text = textField.text.toString()
            return when {
                isEmpty(text) -> {
                    textField.error = "$fieldName can't be empty"
                    false
                }
                checkDefaultValue<T>(fieldValue, defaultValue) -> {
                    textField.error = "Please select an $fieldName item first"
                    false
                }
                else -> {
                    textField.error = null
                    true
                }
            }
        }


        @SuppressLint("SimpleDateFormat")
        fun checkDateFormat(date: String): Boolean {
            val regex = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$"
            val matcher = Pattern.compile(regex).matcher(date)

            if (!matcher.matches()){
                return false
            }
            val format = SimpleDateFormat("dd/MM/yyyy")
            return try {
                format.parse(date)
                true
            } catch (e: ParseException) {
                e.printStackTrace()
                false
            }
        }


    }
}