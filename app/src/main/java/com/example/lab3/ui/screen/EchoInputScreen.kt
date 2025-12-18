package com.example.lab3.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.lab3.entity.Category
import com.example.lab3.entity.Food
import com.example.lab3.viewmodel.db.AppViewModel

@Composable
fun EchoInputScreen(viewModel: AppViewModel) {
    val foods by viewModel.allFoodItems.observeAsState(emptyList())
    val categories by viewModel.allCategories.observeAsState(emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var editFood by remember { mutableStateOf<Food?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { showAddDialog = true }) {
            Text("Додати товар")
        }
        Spacer(modifier = Modifier.height(16.dp))

        foods.forEach { food ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("${food.foodName} \n(${food.categoryName})")
                        Text("Ціна: ${food.price}₴")
                    }
                    Row {
                        Button(onClick = { editFood = Food(food.foodId, food.foodName, food.price, food.categoryId) }) {
                            Text("Змінити")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            viewModel.allFoodItems.value?.firstOrNull { it.foodId == food.foodId }?.let {
                                viewModel.delete(Food(it.foodId, it.foodName, it.price, it.categoryId))
                            }
                        }) {
                            Text("Видалити")
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog && categories.isNotEmpty()) {
        FoodDialog(
            categories = categories,
            onDismiss = { showAddDialog = false },
            onSave = { name, price, categoryId ->
                viewModel.insert(Food(0, name, price.toFloat(), categoryId.toLong()))
                showAddDialog = false
            }
        )
    }

    editFood?.let { food ->
        FoodDialog(
            initialFood = food,
            categories = categories,
            onDismiss = {editFood = null},
            onSave = { name, price, categoryId ->
                viewModel.update(Food(food.id, name, price.toFloat(), categoryId.toLong()))
                editFood = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDialog(
    initialFood: Food? = null,
    categories: List<Category>,
    onDismiss: () -> Unit,
    onSave: (name: String, price: String, categoryId: Int) -> Unit
) {
    var name by remember { mutableStateOf(initialFood?.name ?: "") }
    var price by remember { mutableStateOf(initialFood?.price?.toString() ?: "") }
    var selectedCategory by remember {
        mutableStateOf(initialFood?.let { food -> categories.find { it.id == food.categoryId } } ?: categories.firstOrNull())
    }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialFood == null) "Додати товар" else "Змінити товар") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Назва") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Ціна") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Категорія") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategory = category
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick =
                {
                    if (name.isNotBlank() && price.isNotBlank()) {
                        selectedCategory?.let { category ->
                        onSave(name, price, category.id.toInt()) }
                    }
                }
            ) { Text("Зберегти") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Скасувати") }
        }
    )
}
