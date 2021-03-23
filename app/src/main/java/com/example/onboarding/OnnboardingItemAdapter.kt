package com.example.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnnboardingItemAdapter(private val onboardingItems: List<OnboardingItem>) :
RecyclerView.Adapter<OnnboardingItemAdapter.OnboardingItemView>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemView {
        return OnboardingItemView(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.onboarding_item_container,parent,false
                )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemView, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnboardingItemView(view: View) : RecyclerView.ViewHolder(view){
        private val imagerOnboarding = view.findViewById<ImageView>(R.id.imageOnboarding);
        private val textTitle = view.findViewById<TextView>(R.id.textTitle);
        private val textDescription = view.findViewById<TextView>(R.id.textDescription);

        fun bind(onboardingItem: OnboardingItem){
            imagerOnboarding.setImageResource(onboardingItem.onboardingImage);
            textTitle.setText(onboardingItem.title);
            textDescription.setText(onboardingItem.description);

        }
    }
}