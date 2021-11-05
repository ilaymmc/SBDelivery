package ru.skillbranch.sbdelivery.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.skillbranch.sbdelivery.core.adapter.ProductDelegate
import ru.skillbranch.sbdelivery.core.decor.GridPaddingItemDecoration
import ru.skillbranch.sbdelivery.databinding.FragmentSearchBinding
import ru.skillbranch.sbdelivery.ui.main.MainState

class SearchFragment : Fragment() {
    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModel()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        ProductDelegate().createAdapter {
            // TODO handle click
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initState()
        viewModel.state.observe(viewLifecycleOwner, ::renderState)
        binding.rvProductGrid.adapter = adapter
        binding.rvProductGrid.addItemDecoration(GridPaddingItemDecoration(17))
        val searchEvent = binding.searchInput.queryTextChanges().skipInitialValue().map { it.toString() }
        viewModel.setSearchEvent(searchEvent)
    }

    private fun renderState(state: SearchState) {
//        Log.e("renderState", "$state")
        binding.progressLoading.isVisible = state is SearchState.Loading
        binding.rvProductGrid.isVisible = state is SearchState.Result
        binding.tvErrorMessage.isVisible = state is SearchState.Error
        binding.btnRetry.isVisible = state is SearchState.Error

        if (state is SearchState.Result) {
            adapter.items = state.items
            adapter.notifyDataSetChanged()
        }else if (state is SearchState.Error) {
            binding.tvErrorMessage.text = state.errorDescription
        }
    }

    override fun onDestroyView() {
        binding.rvProductGrid.adapter = null
        _binding = null
        super.onDestroyView()
    }

}