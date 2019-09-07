package nl.rijksmuseum.screens.profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import nl.rijksmuseum.R
import nl.rijksmuseum.core.base.BaseActivity
import nl.rijksmuseum.databinding.ActivityProfileBinding
import nl.rijksmuseum.utils.ext.observe
import javax.inject.Inject

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ProfileViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        subscribeUI()
    }

    private fun initBinding(){
        binding.apply {
            lifecycleOwner = this@ProfileActivity
            viewModel = this@ProfileActivity.viewModel
        }
    }

    private fun subscribeUI() {
        observe(viewModel.getProfile()) { profile ->
            binding.apply {
                profileGithub.setOnClickListener { openWebView(profile.githubUrl) }
                profileEmail.setOnClickListener { goToEmailIntent(profile.email) }
                profileWhatsapp.setOnClickListener { goToWhatsAppIntent(profile.phone) }
            }
        }
    }

    private fun goToEmailIntent(email: String) {

    }

    private fun openWebView(url: String) {

    }

    private fun goToWhatsAppIntent(phone: String) {

    }
}