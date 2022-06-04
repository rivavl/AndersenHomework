package com.marina.andersenhomework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.marina.andersenhomework.MainActivity
import com.marina.andersenhomework.R
import com.marina.andersenhomework.entity.Contact

class ContactFragment : Fragment() {

    private var index: Int? = null

    private lateinit var tvName: EditText
    private lateinit var tvNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt(INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvName = view.findViewById(R.id.name) as EditText
        tvNumber = view.findViewById(R.id.number) as EditText

        tvName.setText((requireActivity() as MainActivity).contacts[index!!].name)
        tvNumber.setText((requireActivity() as MainActivity).contacts[index!!].number)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).contacts[index!!] =
            Contact(tvName.text.toString(), tvNumber.text.toString())
    }

    companion object {
        private const val INDEX = "index"

        @JvmStatic
        fun newInstance(index: Int) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putInt(INDEX, index)
                }
            }
    }
}