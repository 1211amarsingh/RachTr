package com.kv.rachtr.presentation.user

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.kv.atmapplication.util.isNetworkAvailable
import com.kv.rachtr.MyApplication
import com.kv.rachtr.R
import com.kv.rachtr.databinding.HomeFragmentBinding
import com.kv.rachtr.presentation.main.MainActivity


class HomeFragment : Fragment() {

    val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            (requireActivity().application as MyApplication).homeRepository,
            (requireActivity().application as MyApplication).dataStoreManager
        )
    }
    lateinit var binding: HomeFragmentBinding
    lateinit var todoAdapter: TodoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<HomeFragmentBinding>(
            inflater,
            R.layout.home_fragment,
            container,
            false
        ).apply {
            viewmodel = viewModel
        }
        binding.lifecycleOwner = this
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoAdapter = TodoAdapter()
        binding.rvTask.adapter = todoAdapter

        viewModel.tododata.observe(viewLifecycleOwner, {
            todoAdapter.submitList(it)
        })
        if (isNetworkAvailable(requireContext())) {
            viewModel.getTodoData()
        } else {
            Snackbar.make(binding.root, "No internet Connection", Snackbar.LENGTH_SHORT).show()
        }
        viewModel.user.observe(viewLifecycleOwner, {
            (activity as MainActivity).supportActionBar?.setTitle(it.userName.toUpperCase() + " " + it.userType)

        })

    }
}