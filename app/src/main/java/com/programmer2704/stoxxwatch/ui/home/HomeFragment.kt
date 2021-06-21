package com.programmer2704.stoxxwatch.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmer2704.stoxxwatch.adapter.WatchListAdapter
import com.programmer2704.stoxxwatch.app.App
import com.programmer2704.stoxxwatch.data.models.WatchListItem
import com.programmer2704.stoxxwatch.databinding.FragmentHomeBinding
import com.programmer2704.stoxxwatch.ui.search.Search
import com.programmer2704.stoxxwatch.ui.viewstock.ViewStock
import com.programmer2704.stoxxwatch.util.ClickListener
import com.programmer2704.stoxxwatch.util.Constants
import com.programmer2704.stoxxwatch.util.ItemTouchListener
import com.programmer2704.stoxxwatch.util.SwipeToDeleteCallback
import javax.inject.Inject

class HomeFragment : Fragment(), HomeContract.View {

    @Inject
    lateinit var presenter: HomePresenter
    lateinit var component: HomeComponent
    lateinit var viewAdapter: WatchListAdapter
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAnimator: RecyclerView.ItemAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = (activity?.applicationContext as App).component.homeComponent().create()
        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
        presenter.getWatchListItems()
    }

    override fun onPause() {
        super.onPause()
        presenter.drop()
    }

    private fun initUi(){
        binding.searchBtn.setOnClickListener {
            val intent = Intent(activity?.applicationContext, Search::class.java)
            startActivity(intent)
        }

        presenter.getWatchListItems()
        viewAdapter = WatchListAdapter(listOf())
        viewManager = LinearLayoutManager(context, RecyclerView.VERTICAL ,false)
        viewAnimator = DefaultItemAnimator()
        binding.watchListRv.apply {
            adapter = viewAdapter
            itemAnimator = viewAnimator
            layoutManager = viewManager
            setHasFixedSize(true)
            scrollToPosition(viewAdapter.itemCount - 1)
        }

        binding.watchListRv.addOnItemTouchListener(ItemTouchListener(
            context, binding.watchListRv,
            object: ClickListener {
            override fun onClick(view: View?, position: Int) {
                val item = viewAdapter.get(position)
                val intent = Intent(this@HomeFragment.context, ViewStock::class.java)
                intent.putExtra(Constants.EXTRA_STOCK_NAME, item.name)
                intent.putExtra(Constants.EXTRA_STOCK_SYMBOL, item.symbol)
                startActivity(intent)
            }
            override fun onLongClick(view: View?, position: Int) = Unit
        }))

        val handler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = viewAdapter.get(viewHolder.adapterPosition)
                presenter.deleteItem(item, viewHolder.adapterPosition)
                return
            }
        }
        ItemTouchHelper(handler).attachToRecyclerView(binding.watchListRv)
    }

    override fun onWatchListItems(items: List<WatchListItem>){
        if(items.isNotEmpty()){
            items.forEach(::println)
            binding.watchListRv.visibility = View.VISIBLE
            binding.watchListTv.visibility = View.VISIBLE
            binding.emptyWatchlistTv.visibility = View.GONE
            viewAdapter.updateList(items)
            viewAdapter.notifyDataSetChanged()
        }else{
            binding.emptyWatchlistTv.visibility = View.VISIBLE
        }
    }

    override fun onItemDeleted(adapterPosition: Int) {
        viewAdapter.delete(adapterPosition)
        viewAdapter.notifyItemRemoved(adapterPosition)
        if(viewAdapter.itemCount == 0) {
            binding.emptyWatchlistTv.visibility = View.VISIBLE
            binding.watchListTv.visibility = View.GONE
            binding.watchListRv.visibility = View.GONE
        }
    }

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initUi()

        val watchListTv: TextView = binding.watchListTv
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            watchListTv.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}