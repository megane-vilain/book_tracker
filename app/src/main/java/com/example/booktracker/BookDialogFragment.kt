package com.example.booktracker

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

private const val ARG_ISBN = "isbn"

/**
 * A simple [Fragment] subclass.
 * Use the [BookDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var isbn: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isbn= it.getString(ARG_ISBN)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val dialogView = inflater.inflate(R.layout.fragment_book_dialog, container, false)
        val bookTitleTextView = dialogView.findViewById<TextView>(R.id.bookTitle)
        val authorTextView = dialogView.findViewById<TextView>(R.id.bookAuthor)
        val summaryTextView = dialogView.findViewById<TextView>(R.id.bookSummary)
        val coverImageView = dialogView.findViewById<ImageView>(R.id.bookCover)

        val isbn = arguments?.getString(ARG_ISBN)


        lifecycleScope.launch {
            val book = RetrofitClient.isbnApi.getBookById(isbn.toString())
            if (book.authors.isNotEmpty()){
                val authorId = book.authors[0].key
                val author = RetrofitClient.authorApi.getAuthorById(authorId)
                authorTextView.text = author.name
            }
            if (book.works.isNotEmpty()){
                var work = book.works[0]
                work = RetrofitClient.authorApi.getWorkById(work.key)
                summaryTextView.text = work.description
            }
            bookTitleTextView.text = book.title
            Glide.with(requireContext()).load("https://covers.openlibrary.org/b/isbn/${isbn}-M.jpg").into(coverImageView)

        }

        return dialogView
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val metrics = resources.displayMetrics
            val width = (metrics.widthPixels * 0.7).toInt()  // 50% of screen width
            val height = (metrics.heightPixels * 0.6).toInt() // 60% of screen height

            window.setLayout(width, height)
            window.setBackgroundDrawableResource(android.R.color.transparent) // optional for rounded corners
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param isbn Parameter 1.
         * @return A new instance of fragment BookDialogFragment.
         */
        @JvmStatic
        fun newInstance(isbn: String?) =
            BookDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ISBN, isbn)
                }
            }
    }

}