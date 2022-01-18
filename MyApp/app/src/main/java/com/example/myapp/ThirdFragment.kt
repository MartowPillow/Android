package com.example.myapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.FragmentThirdBinding
import com.squareup.picasso.Picasso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imgUrl: String = arguments?.getString("imgUrl").toString()
        Picasso.with(context)
            .load(imgUrl)
            .into(binding.validView);

        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }

        binding.validButton.setOnClickListener {
            val name: String = binding.inputName.text.toString()
            val cat: Cell = Cell(name,imgUrl)
            val txt: String = Json.encodeToString(cat)

            //TODO: Serialize cat
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            val edit = sharedPref?.edit()
            edit?.putString("json" , txt)
            edit?.apply()
            val truc = sharedPref?.all
            println(truc)


            val toast = Toast.makeText(
                activity?.applicationContext,
                "Cat successfully added to favorites!",
                Toast.LENGTH_LONG
            )
            toast.show()
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}