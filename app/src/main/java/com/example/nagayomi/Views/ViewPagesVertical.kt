package com.example.nagayomi.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.nagayomi.Views.Adapters.VerticalPageAdapter
import com.example.nagayomi.Models.MangaDexApi.ApiClient
import com.example.nagayomi.Models.MangaDexApi.NagaYomiModel
import com.example.nagayomi.NagaYomiContract
import com.example.nagayomi.Presenter.PagesPresenter
import com.example.nagayomi.databinding.FragmentViewPagesVerticalBinding
import kotlinx.coroutines.launch

import java.io.IOException

class ViewPagesVertical(
    private val chapterId: String,
    nextChapterListener: ViewPagesHorizontal.NextChapterListener
) : Fragment(), NagaYomiContract.PageView {
    private var _binding: FragmentViewPagesVerticalBinding? = null
    private lateinit var showPagesAdapter: VerticalPageAdapter
    private val binding get() = _binding!!
    private val listener: ViewPagesHorizontal.NextChapterListener = nextChapterListener
    private lateinit var presenter: PagesPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagesVerticalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = PagesPresenter(this, NagaYomiModel())
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            presenter.getChapterPages(chapterId)
        }
        binding.fabNextChapter.setOnClickListener {
            listener.onNextChapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showPages(hash: String, pages: List<String>) {
        try {
            showPagesAdapter = VerticalPageAdapter()
            showPagesAdapter.submitData(hash, pages)
            binding.rvPages.adapter = showPagesAdapter
            binding.rvPages.layoutManager = LinearLayoutManager(context)
            binding.rvPages.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        binding.fabNextChapter.visibility = View.VISIBLE
                    } else {
                        binding.fabNextChapter.visibility = View.GONE
                    }
                }
            }
            )
        }catch (e : IOException){
            e.printStackTrace()
        }
    }
}