package com.example.miniproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.R
import com.example.miniproject.adapters.ArticleRecyclerViewAdapter
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.model.SourceModel
import com.example.miniproject.viewModel.ArticleViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {
    private var sourceModel: SourceModel? = null
    private var progressBar: ProgressBar? = null
    private var articleRecyclerViewAdapter: ArticleRecyclerViewAdapter? = null
    private var articleRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            sourceModel = requireArguments().getSerializable(SOURCE_MODEL) as SourceModel?
            articleRecyclerViewAdapter = ArticleRecyclerViewAdapter(requireActivity())
        }
    }

    private fun setUpSourcesList() {
        showProgressBar()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        var articleModels: LiveData<List<ArticleModel?>?>?
        val articleViewModel = ArticleViewModel(requireActivity().application)
        articleViewModel.callApiAndSaveInDB(sourceModel!!.id)
        articleModels = articleViewModel.getArticles(sourceModel!!.id)
        hideProgressBar()
        articleModels?.observe(this, { sourceModels ->
            articleRecyclerViewAdapter!!.setArticlesList(sourceModels as List<ArticleModel>?)
            articleRecyclerViewAdapter!!.notifyDataSetChanged()
        })
    }

    private fun hideProgressBar() {
        progressBar!!.visibility = View.GONE
        articleRecyclerView!!.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE
        articleRecyclerView!!.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        articleRecyclerView = view.findViewById(R.id.articles_recyclerView)
        progressBar = view.findViewById(R.id.progress_circular)
        articleRecyclerView?.layoutManager = LinearLayoutManager(activity)
        articleRecyclerView?.adapter = articleRecyclerViewAdapter
        return view
    }

    override fun onResume() {
        super.onResume()
        if (sourceModel != null) {
            setUpSourcesList()
        }
    }

    companion object {
        private const val SOURCE_MODEL = "source_model"
        fun newInstance(sourceModel: SourceModel?): ArticleFragment {
            val fragment = ArticleFragment()
            val arguments = Bundle()
            arguments.putSerializable(SOURCE_MODEL, sourceModel)
            fragment.arguments = arguments
            return fragment
        }
    }
}