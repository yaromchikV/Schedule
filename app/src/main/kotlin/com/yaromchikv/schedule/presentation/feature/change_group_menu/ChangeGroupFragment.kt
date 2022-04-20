package com.yaromchikv.schedule.presentation.feature.change_group_menu

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.yaromchikv.domain.model.GroupModel
import com.yaromchikv.schedule.R
import com.yaromchikv.schedule.databinding.FragmentChangeGroupBinding
import com.yaromchikv.schedule.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChangeGroupFragment : Fragment(R.layout.fragment_change_group) {

    private val binding by viewBinding(FragmentChangeGroupBinding::bind)

    private val mainViewModel by sharedViewModel<MainViewModel>()
    //private val changeGroupViewModel by viewModel<ChangeGroupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.composeList.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val groups by mainViewModel.groups.collectAsState()
                    groups?.let { list ->
                        MyComposeList(itemViewStates = list) { group ->
                            mainViewModel.selectGroupClick(group)
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MyComposeList(
        modifier: Modifier = Modifier,
        itemViewStates: List<GroupModel>,
        itemClickedCallback: (groupModel: GroupModel) -> Unit
    ) {
        LazyColumn(modifier = modifier.padding(top = 58.dp)) {
            items(itemViewStates) { groupModel ->
                MyComposeListItem(
                    group = groupModel,
                    itemClickedCallback = itemClickedCallback
                )
            }
        }
    }

    @Composable
    fun MyComposeListItem(
        group: GroupModel,
        itemClickedCallback: (groupModel: GroupModel) -> Unit
    ) {
        Box(Modifier.padding(top = 10.dp).clickable(onClick = { itemClickedCallback(group) })) {
            Card(
                elevation = 2.dp,
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row() {
                        Text(
                            modifier = Modifier.wrapContentHeight().wrapContentWidth()
                                .padding(16.dp),
                            color = Color.Black,
                            text = getString(R.string.group_name, group.name, group.speciality),
                            fontSize = 18.sp
                        )
                        val selected by mainViewModel.selectedGroup.collectAsState()
                        if (group.id == selected?.id) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(top = 4.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {
                                Image(
                                    painterResource(R.drawable.ic_check),
                                    contentDescription = "",
                                    modifier = Modifier.size(48.dp).padding(end = 16.dp),
                                    colorFilter = ColorFilter.tint(Color(0xFF00998d))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}