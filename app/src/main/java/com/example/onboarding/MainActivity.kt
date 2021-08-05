package com.example.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemAdapter: OnnboardingItemAdapter

    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicator()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems() {
        onboardingItemAdapter = OnnboardingItemAdapter(
                listOf(
                        OnboardingItem(
                                onboardingImage = R.drawable.gf,
                                title = "BRAGA CLUB",
                                description = "Musuh Hinata"
                        ),
                        OnboardingItem(
                                onboardingImage = R.drawable.game,
                                title = "Gaming Community",
                                description = "Mobile Legend"
                        ),
                        OnboardingItem(
                                onboardingImage = R.drawable.visi,
                                title = "VISI MISI",
                                description = "Troll Dan KS Kill Teman"
                        )
                )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imageNext).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingItemAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
            } else {
                navigateToLoginRegisterActivity()
            }
        }

        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            navigateToLoginRegisterActivity()
        }

    }

    private fun navigateToLoginRegisterActivity() {
        startActivity(Intent(applicationContext, LoginRegisterActivity::class.java))
        finish()
    }

    private fun setupIndicator() {
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
                LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(24, 0, 24, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.indicator_inactive_background
                        )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.indicator_active_background
                        )
                )
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.indicator_inactive_background
                        )
                )
            }
        }
    }
}