package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


class NewFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewBinding.inflate(inflater,container,false)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            if(!binding.edit.text.isNullOrBlank()){
                val text = binding.edit.text.toString()
                viewModel.changeContent(text)
                AndroidUtils.hideKeyboard(binding.root)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
}