package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
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
import coil.load
import com.example.myapp.databinding.FragmentThirdBinding
import com.squareup.picasso.Picasso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement

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
        if(imgUrl != "") {
            binding.validView.load(imgUrl)
        }
        else{
            val toast = Toast.makeText(
                activity?.applicationContext,
                "You have to get a cat to add it to your favorites",
                Toast.LENGTH_LONG
            )
            toast.show()
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }

        binding.validButton.setOnClickListener {
            val name: String = binding.inputName.text.toString()
            if(name != "") {
                val cat: Cell = Cell(name, imgUrl)
                val jsonCat: JsonElement = Json.encodeToJsonElement(cat)

                val sharedPref = context?.getSharedPreferences("global", Context.MODE_PRIVATE)
                var stringCatArray = sharedPref?.getString("json", "")
                val jsonCatArray: JsonArray = Json.decodeFromString<JsonArray>(stringCatArray!!)
                stringCatArray = Json.encodeToString(jsonCatArray.plus(jsonCat))

                val edit = sharedPref?.edit()
                edit?.putString("json", stringCatArray)
                edit?.apply()
                println(sharedPref?.all)

                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "Cat successfully added to favorites!",
                    Toast.LENGTH_LONG
                )
                toast.show()
                findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
            }
            else{
                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "You must give this cat a name!",
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}