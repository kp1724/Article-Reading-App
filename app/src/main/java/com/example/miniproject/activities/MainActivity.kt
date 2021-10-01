package com.example.miniproject.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.R
import com.example.miniproject.adapters.SourceRecyclerViewAdapter
import com.example.miniproject.fragments.ArticleFragment
import com.example.miniproject.model.ArticleModel
import com.example.miniproject.model.SourceModel
import com.example.miniproject.viewModel.SourceViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mContext: Context? = null
    private var progressBar: ProgressBar? = null
    private var fragmentContainer: FrameLayout? = null
    private var sourceRecyclerView: RecyclerView? = null
    private var sourceRecyclerViewAdapter: SourceRecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        initViews()
        if (savedInstanceState == null) {
            sourceRecyclerViewAdapter = SourceRecyclerViewAdapter(mContext)
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
        val sourceViewModel = SourceViewModel(application)
        val sourceModelList = sourceViewModel.getSourceList()
        sourceViewModel.callApiAndSaveInDB()
        hideProgressBarAndShowSourceList()
        sourceModelList?.observe((mContext as LifecycleOwner?)!!, { sourceModels ->
            sourceRecyclerViewAdapter?.setSourcesList(sourceModels)
            sourceRecyclerViewAdapter?.notifyDataSetChanged()
        })
    }

    private fun hideProgressBarAndShowSourceList() {
        progressBar!!.visibility = View.GONE
        sourceRecyclerView!!.visibility = View.VISIBLE
        fragmentContainer!!.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE
        sourceRecyclerView!!.visibility = View.GONE
        fragmentContainer!!.visibility = View.GONE
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
        progressBar!!.visibility = View.GONE
        sourceRecyclerView!!.visibility = View.GONE
        fragmentContainer!!.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (fragmentContainer!!.visibility == View.VISIBLE) {
            hideProgressBarAndShowSourceList()
            supportFragmentManager.popBackStack()
        } else finish()
    }
}