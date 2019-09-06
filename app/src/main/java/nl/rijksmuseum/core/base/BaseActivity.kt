package nl.rijksmuseum.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<B : ViewDataBinding> constructor(
    @LayoutRes val layoutRes: Int
) : DaggerAppCompatActivity() {

    protected lateinit var binding: B

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, layoutRes)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
    }
}