package com.nexters.sticky.ui.dialog

import com.nexters.sticky.R

enum class DialogType(val titleRes: Int, val descriptionRes: Int, val doActionRes: Int) {
	TERMINATE_CHALLENGE(R.string.dialog_terminate_challenge_title, R.string.dialog_terminate_challenge_description, R.string.dialog_share),
	EXIT_CHALLENGE(R.string.dialog_exit_challenge_title, R.string.dialog_exit_challenge_description, R.string.dialog_exit),
	CHANGE_ADDRESS(R.string.dialog_change_address_title, R.string.dialog_change_address_description, R.string.dialog_exit);
}