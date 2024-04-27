package com.example.nagayomi.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.nagayomi.Views.Adapters.HorizontalPageAdapter
import com.example.nagayomi.Models.MangaDexApi.NagaYomiModel
import com.example.nagayomi.NagaYomiContract
import com.example.nagayomi.Presenter.PagesPresenter
import com.example.nagayomi.databinding.FragmentViewPagesHorizontalBinding
import kotlinx.coroutines.launch
import java.io.IOException

class ViewPagesHorizontal(
    private val chapterId: String,
    nextChapterListener: NextChapterListener
) : Fragment(), NagaYomiContract.PageView {
    private var _binding: FragmentViewPagesHorizontalBinding? = null
    private lateinit var showPagesAdapter: HorizontalPageAdapter
    private val binding get() = _binding!!
    private val listener: NextChapterListener = nextChapterListener
    private lateinit var presenter: PagesPresenter
    interface NextChapterListener {
        fun onNextChapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagesHorizontalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = PagesPresenter(this, NagaYomiModel())
        super.onViewCreated(view, savedInstanceState)

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
            showPagesAdapter = HorizontalPageAdapter()
            showPagesAdapter.submitData(hash, pages)
            binding.viewPagerHorizontal.adapter = showPagesAdapter
            binding.viewPagerHorizontal.registerOnPageChangeCallback(object : OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == showPagesAdapter.itemCount.minus(1)){
                        binding.fabNextChapter.visibility = View.VISIBLE
                    }else{
                        binding.fabNextChapter.visibility = View.GONE
                    }
                }
            })
        }catch (e : IOException){
            e.printStackTrace()
        }
    }
}