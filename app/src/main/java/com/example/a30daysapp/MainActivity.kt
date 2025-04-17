package com.example.a30daysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30daysapp.data.Datasource
import com.example.a30daysapp.model.Exercise
import com.example.a30daysapp.ui.theme._30DaysAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _30DaysAppTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ){
                        ExercisesApp()
                    }

            }
        }
    }
}

@Composable
fun ExercisesApp() {
    ExerciseList(
        exerciseList = Datasource().loadExercises(),
    )
}

@Composable
fun ExerciseList(exerciseList: List<Exercise>, modifier: Modifier = Modifier) {
    Scaffold(
    topBar = {
        ExerciseAppBar(modifier)
        }
    ) {it ->
        LazyColumn(contentPadding = it) {
            items(exerciseList) {
                ExerciseCard(
                    exercise = it,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }

}

@Composable
fun ExerciseCard(
    exercise: Exercise,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer,
    )
    Card(modifier = Modifier
        .padding(bottom = 10.dp, end = 10.dp, start = 10.dp)) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,
                    )
                )
                .background(color = color)
        ){
            Text(
                text = LocalContext.current.getString((exercise.stringResourceDay)),
                modifier = Modifier.padding((6.dp)),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = LocalContext.current.getString((exercise.stringResourceId)),
                modifier = Modifier.padding((6.dp)),
                style = MaterialTheme.typography.headlineSmall
            )
            ExerciseCardButton(
                expanded = expanded,
                onClick = {expanded = !expanded}
            )
            Image(
                painter = painterResource(exercise.imageResourceId),
                contentDescription = stringResource(exercise.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
                    .padding(start = 10.dp, end = 10.dp),
                contentScale = ContentScale.Crop
            )
            if(expanded) {
                ExerciseDescription(
                    exercise.stringResourceExerciseDescrip,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displaySmall
                )
            }
        },
        modifier = modifier
    )
}


@Composable
fun ExerciseDescription(
    @StringRes exerciseDescription: Int,
    modifier: Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text =  stringResource(exerciseDescription),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ExerciseCardButton(
    expanded:Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun ExerciseCardPreview() {
    _30DaysAppTheme(darkTheme = false) {
        ExercisesApp()
    }
}

@Preview
@Composable
fun ExerciseCarDarkThemedPreview() {
    _30DaysAppTheme(darkTheme = true) {
        ExercisesApp()
    }
}