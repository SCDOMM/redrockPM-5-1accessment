package com.example.xiwangnotepad.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.xiwangnotepad.view.FragmentInterface
import com.example.xiwangnotepad.MainActivity

/**   
 * 包名称： com.example.xiwangnotepad.view.adapter
 * 类名称：MainAdapter
 * 类描述：TODO
 * 创建人：韦西波
 * 创建时间：2026-05-01 12:21
 *
 */
class MainAdapter(val fragments: ArrayList<FragmentInterface>, mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {

    override fun createFragment(p0: Int): Fragment {
        return fragments[p0].back()
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}