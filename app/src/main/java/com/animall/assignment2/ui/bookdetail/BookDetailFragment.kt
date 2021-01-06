package com.animall.assignment2.ui.bookdetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.databinding.FragmentBookDetailsBinding
import com.animall.assignment2.util.Resource
import com.animall.assignment2.util.autoCleared
import com.animall.assignment2.viewmodel.BookDetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private var binding: FragmentBookDetailsBinding by autoCleared()
    private val viewModel: BookDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.book.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindBookDetails(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.clBookDetails.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.clBookDetails.visibility = View.GONE
                }
            }
        })
    }

    private var shouldLoadImage = true

    private fun bindBookDetails(book: Book) {
        binding.bookTitle.text = book.title

        val authorsString = book.authors.toString()
        binding.bookAuthor.text = authorsString.substring(1, authorsString.length - 1)

        binding.bookPublisher.text = book.publisher
        binding.bookNumpages.text = book.pageCount.toString()

        if(shouldLoadImage) {
            shouldLoadImage = false
            Glide.with(binding.root)
                .load(book.thumbnail)
                .into(binding.image)
        }
    }
}