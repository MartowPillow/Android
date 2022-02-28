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
 * Add cat to your favorites
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
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

        //Get image Url from navigation
        var imgUrl: String = arguments?.getString("imgUrl").toString()
        if(imgUrl != null && imgUrl != "") {
            binding.validView.load(imgUrl) //Display image on screen
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

        //Go to 'random cats' page
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
        }

        binding.validButton.setOnClickListener {
            //Read given name from textbox
            val name: String = binding.inputName.text.toString()
            if(name != "") {
                //Create cat
                val cat: Cell = Cell(name, imgUrl)
                val jsonCat: JsonElement = Json.encodeToJsonElement(cat)

                //Get favorites from shared preferences
                val sharedPref = context?.getSharedPreferences("global", Context.MODE_PRIVATE)
                var stringCatArray = sharedPref?.getString("json", "")
                val jsonCatArray: JsonArray = Json.decodeFromString<JsonArray>(stringCatArray!!)

                //Add cat to favorites
                stringCatArray = Json.encodeToString(jsonCatArray.plus(jsonCat))

                //Save new favorites in shared preferences
                val edit = sharedPref?.edit()
                edit?.putString("json", stringCatArray)
                edit?.apply()

                val toast = Toast.makeText(
                    activity?.applicationContext,
                    "Cat successfully added to favorites!",
                    Toast.LENGTH_LONG
                )
                toast.show()
                //Go back to first page
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