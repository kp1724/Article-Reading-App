package com.example.miniproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.R
import com.example.miniproject.activities.MainActivity
import com.example.miniproject.adapters.ArticleRecyclerViewAdapter
import com.example.miniproject.extension.KotlinExtension.ViewExtension.gone
import com.example.miniproject.extension.KotlinExtension.ViewExtension.visible
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.model.SourceModel
import com.example.miniproject.utils.ViewModelFactory
import com.example.miniproject.viewModel.ArticleViewModel
import javax.inject.Inject

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

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
        val articleViewModel = ViewModelProvider(this, viewModelFactory)[ArticleViewModel::class.java]
        articleViewModel.callApiAndSaveInDB(sourceModel?.id)
        val articleModels: LiveData<List<ArticleModel?>?>? = articleViewModel.getArticles(sourceModel?.id)
        hideProgressBar()
        articleModels?.observe(this, { sourceModels ->
            articleRecyclerViewAdapter!!.setArticlesList(sourceModels as List<ArticleModel>?)
            articleRecyclerViewAdapter!!.notifyDataSetChanged()
        })
    }

    private fun hideProgressBar() {
        progressBar.gone()
        articleRecyclerView.visible()
    }

    private fun showProgressBar() {
        progressBar.visible()
        articleRecyclerView.gone()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.appComponent?.inject(this)
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