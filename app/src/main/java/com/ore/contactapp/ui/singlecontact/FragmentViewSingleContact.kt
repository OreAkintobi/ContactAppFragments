package com.ore.contactapp.ui.singlecontact


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.FragmentViewSingleContactBinding

class FragmentViewSingleContact : Fragment() {
    private lateinit var viewModel: SingleContactViewModel
    private lateinit var viewModelFactory: SingleContactViewModelFactory
    private lateinit var binding: FragmentViewSingleContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_view_single_contact,
                container,
                false
            )

        val args =
            FragmentViewSingleContactArgs.fromBundle(
                arguments!!
            )

        viewModelFactory = SingleContactViewModelFactory(args.contact)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get((SingleContactViewModel::class.java))
        binding.singleContactViewModel = viewModel
        binding.lifecycleOwner = this

        binding.contact = args.contact

        Glide.with(this).asBitmap()
            .load("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
            .centerCrop().fallback(
                R.drawable.ic_contact_logo_main
            ).error(R.drawable.ic_add_contact_logo).placeholder(R.drawable.ic_contact_logo_main)
            .into(binding.contactImageTwo)

        binding.callContact.setOnClickListener {
            viewModel.callContact(it)
        }

        binding.emailContact.setOnClickListener {
            viewModel.emailContact(it)
        }

        return binding.root
    }
}
