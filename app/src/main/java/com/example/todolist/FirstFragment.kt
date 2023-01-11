package com.example.todolist

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBindings
import com.example.todolist.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var items: MutableList<String> = mutableListOf();

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listView = binding.listView;

        binding.fab.setOnClickListener { view ->
            val task: EditText = binding.task;
            task?.let {
                val text: String = it.text.toString()
                items.add(text)
                task.text.clear()
                loadList(listView, items);
            }
        }
            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position) as String
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Excluir item da lista")
                builder.setMessage("Deseja excluir $selectedItem ?")
                builder.create()
                val positiveButton = builder.setPositiveButton("OK") { dialog, which ->
                    items.remove(selectedItem)
                    loadList(listView,items)
                    dialog.dismiss()
                }
                val negativeButton = builder.setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(context, "Ação cancelada com sucesso", Toast.LENGTH_SHORT).show()
                }
                val dialog = builder.create()
                dialog.show()
            }

    }
    private fun loadList(listView:ListView, items:List<String>){
        listView.adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, items) };
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}