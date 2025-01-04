package com.application.x_pass.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable
fun LogoutConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Выйти из аккаунта?") },
        text = { Text(text = "Внимание. Вы действительно хотите выйти из аккаунта?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Выйти")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Отменить")
            }
        }
    )
}
