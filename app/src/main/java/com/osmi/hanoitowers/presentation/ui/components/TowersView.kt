package com.osmi.hanoitowers.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TowersView(
    towerA: List<Int>,
    towerB: List<Int>,
    towerC: List<Int>,
    currentMoveIndex: Int,
    totalMoves: Int,
    currentUrl: String,
    onNextMove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
                       Text(
                   text = "Movimiento $currentMoveIndex de $totalMoves",
                   fontSize = 16.sp,
                   fontWeight = FontWeight.Medium,
                   modifier = Modifier.padding(8.dp)
               )
               
               Text(
                   text = currentUrl,
                   fontSize = 12.sp,
                   color = Color.Gray,
                   modifier = Modifier.padding(horizontal = 8.dp)
               )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TowerCard(
                towerName = "A",
                disks = towerA,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )

            TowerCard(
                towerName = "B",
                disks = towerB,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )

            TowerCard(
                towerName = "C",
                disks = towerC,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onNextMove,
                enabled = currentMoveIndex < totalMoves
            ) {
                Text("Siguiente")
            }
        }
    }
}

@Composable
private fun TowerCard(
    towerName: String,
    disks: List<Int>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Torre $towerName",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    drawLine(
                        color = Color(0xFF8D6E63),
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.height),
                        strokeWidth = 8f
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (diskSize in disks) {
                        DiskView(
                            diskSize = diskSize,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun DiskView(
    diskSize: Int,
    modifier: Modifier = Modifier
) {
    val diskWidth = (diskSize * 8 + 40).dp
    val diskHeight = 20.dp
    
    Card(
        modifier = modifier
            .width(diskWidth)
            .height(diskHeight),
        colors = CardDefaults.cardColors(
            containerColor = getDiskColor(diskSize)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = diskSize.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

private fun getDiskColor(size: Int): Color {
    return when (size) {
        1 -> Color(0xFFE57373)
        2 -> Color(0xFF81C784)
        3 -> Color(0xFF64B5F6)
        4 -> Color(0xFFFFB74D)
        5 -> Color(0xFFBA68C8)
        6 -> Color(0xFF4DB6AC)
        7 -> Color(0xFFFF8A65)
        8 -> Color(0xFF9E9E9E)
        else -> Color(0xFF90A4AE)
    }
}
