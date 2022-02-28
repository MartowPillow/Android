package com.example.myapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var cellsList = ArrayList<Cell>()
    private var recyclerView: RecyclerView? = null
    private var recyclerviewCellAdapter: RecyclerviewCellAdapter? = null
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = context?.getSharedPreferences("global",Context.MODE_PRIVATE)
        val initialCats = sharedPref?.getString("json","")
        myViewModel.setInitialCats(initialCats)
        myViewModel.getCells().observe(viewLifecycleOwner, Observer<List<Cell>>{ cells ->
            cellsList.addAll(cells)

            if(cellsList.size == 0){
                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "You have no favorite cats yet. Go add some then come back here!",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        })

        recyclerView = view.findViewById(R.id.favRecycler)

        recyclerviewCellAdapter = RecyclerviewCellAdapter(cellsList, context)
        recyclerView?.setHasFixedSize(true)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(view.context)

        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setItemAnimator(DefaultItemAnimator())
        recyclerView?.setAdapter(recyclerviewCellAdapter)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}