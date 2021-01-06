package com.animall.assignment2.ui.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.animall.assignment2.util.Resource
import com.animall.assignment2.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import com.animall.assignment2.R
import com.animall.assignment2.databinding.FragmentBookListBinding
import com.animall.assignment2.viewmodel.BookListViewModel

@AndroidEntryPoint
class BookListFragment : Fragment(), BooksAdapter.BookItemListener {

    private var binding: FragmentBookListBinding by autoCleared()
    private val viewModel: BookListViewModel by viewModels()
    private lateinit var adapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = BooksAdapter(this)
        binding.rvBooks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBooks.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.books.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onClickedBook(bookId: String) {
        findNavController().navigate(
            R.id.action_booksFragment_to_bookDetailsFragment,
            bundleOf("id" to bookId)
        )
    }
}