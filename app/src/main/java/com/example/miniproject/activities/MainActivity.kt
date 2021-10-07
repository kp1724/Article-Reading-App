package com.example.miniproject.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.App
import com.example.miniproject.R
import com.example.miniproject.adapters.SourceRecyclerViewAdapter
import com.example.miniproject.di.component.MainActivityComponent
import com.example.miniproject.extension.KotlinExtension.ViewExtension.gone
import com.example.miniproject.extension.KotlinExtension.ViewExtension.isVisible
import com.example.miniproject.extension.KotlinExtension.ViewExtension.visible
import com.example.miniproject.fragments.ArticleFragment
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.model.SourceModel
import com.example.miniproject.utils.ViewModelFactory
import com.example.miniproject.viewModel.SourceViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mContext: Context? = null
    private var progressBar: ProgressBar? = null
    private var fragmentContainer: FrameLayout? = null
    private var sourceRecyclerView: RecyclerView? = null
    private var sourceRecyclerViewAdapter: SourceRecyclerViewAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var appComponent: MainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent.mainActivityComponent().create()
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        initViews()
        if (savedInstanceState == null) {
            sourceRecyclerViewAdapter = SourceRecyclerViewAdapter(this)
            sourceRecyclerView?.layoutManager = LinearLayoutManager(mContext)
            sourceRecyclerView?.adapter = sourceRecyclerViewAdapter
            setUpSourcesList()
        }
    }

    private fun setUpSourcesList() {
        showProgressBar()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val viewModel = ViewModelProvider(this, viewModelFactory)[SourceViewModel::class.java]
        viewModel.callApiAndSaveInDB()
        val sourceModelList = viewModel.getSourceList()
        hideProgressBarAndShowSourceList()
        sourceModelList?.observe(this, { sourceModels ->
            sourceRecyclerViewAdapter?.setSourcesList(sourceModels)
            sourceRecyclerViewAdapter?.notifyDataSetChanged()
        })
    }

    private fun hideProgressBarAndShowSourceList() {
        progressBar.gone()
        sourceRecyclerView.visible()
        fragmentContainer.gone()
    }

    private fun showProgressBar() {
        progressBar.visible()
        sourceRecyclerView.gone()
        fragmentContainer.gone()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progress_circular)
        fragmentContainer = findViewById(R.id.fragment_container)
        sourceRecyclerView = findViewById(R.id.sources_recyclerView)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sources_cardview_parent -> {
                val model = v.tag as SourceModel
                showArticlesListFragment(model)
            }
            R.id.article_cardview_parent -> {
                val articleModel = v.tag as ArticleModel
                showArticleActivity(articleModel)
            }
        }
    }

    private fun showArticleActivity(articleModel: ArticleModel) {
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra("Article_Model", articleModel)
        startActivity(intent)
    }

    private fun showArticlesListFragment(model: SourceModel) {
        val ft = supportFragmentManager.beginTransaction()
        val articleFragment = ArticleFragment.newInstance(model)
        ft.add(R.id.fragment_container, articleFragment)
        ft.addToBackStack("articleFragment")
        ft.commit()
        showFragmentContainer()
    }

    private fun showFragmentContainer() {
        progressBar.gone()
        sourceRecyclerView.gone()
        fragmentContainer.visible()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (fragmentContainer.isVisible()) {
            hideProgressBarAndShowSourceList()
            supportFragmentManager.popBackStack()
        } else finish()
    }
}