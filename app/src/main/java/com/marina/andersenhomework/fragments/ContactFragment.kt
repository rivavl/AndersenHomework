package com.marina.andersenhomework.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.marina.andersenhomework.MainActivity
import com.marina.andersenhomework.R
import com.marina.andersenhomework.entity.Contact

class ContactFragment : Fragment() {

    private var contact: Contact? = null

    private lateinit var tvName: EditText
    private lateinit var tvNumber: EditText
    private lateinit var ivPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact = it.getParcelable(CONTACT)
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
        ivPhoto = view.findViewById(R.id.photo) as ImageView

        tvName.setText(contact?.name)
        tvNumber.setText(contact?.number)
        loadImage(contact?.photoUrl!!, ivPhoto, requireContext())
    }

    private fun loadImage(url: String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).contacts[(requireActivity() as MainActivity).contacts.indexOf(
            contact
        )] =
            Contact(
                contact?.id!!,
                tvName.text.toString(),
                tvNumber.text.toString(),
                contact?.photoUrl!!
            )
    }

    companion object {
        private const val CONTACT = "contact"

        @JvmStatic
        fun newInstance(contact: Contact) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CONTACT, contact)
                }
            }
    }
}