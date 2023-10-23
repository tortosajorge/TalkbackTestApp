package com.example.talkbacktestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityEvent
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talkbacktestapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    val items = List(20) { i ->
        Item(title = "Título ${i + 1}", imageDescription = "Descripción Imagen ${i + 1}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        setupCustomAccessibilityAction()
        setupCustomDirections()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = ItemAdapter(items)
        binding.recyclerView.adapter = adapter
    }

    private fun setupListeners(){
        binding.showButton.setOnClickListener {
            binding.recyclerView.visibility = View.VISIBLE
        }

        binding.hideButton.setOnClickListener {
            binding.recyclerView.visibility = View.INVISIBLE
            binding.recyclerView.announceForAccessibility("lista de películas escondida")
        }
    }

    private fun setupCustomAccessibilityAction(){
        val myAccessibilityDelegate = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val actionLabel =  "entrar en la lista"
                val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_CLICK, actionLabel
                )
                info.addAction(customAction)
            }

            override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
                if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
                    binding.title.requestFocus()
                    binding.title.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                    return true
                }
                return super.performAccessibilityAction(host, action, args)
            }
        }
        ViewCompat.setAccessibilityDelegate(binding.titleRecyclerView, myAccessibilityDelegate)
    }

    private fun setupCustomDirections(){
        binding.showButton.accessibilityTraversalBefore
    }
}
