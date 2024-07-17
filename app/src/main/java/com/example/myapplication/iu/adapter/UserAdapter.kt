package com.example.myapplication.iu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.UserModel
import com.example.myapplication.databinding.ItemUserBinding
import com.squareup.picasso.Picasso

class UserAdapter(
    private val onUserClickListener: OnUserClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    lateinit var context:Context
    private val dataUser: MutableList<UserModel> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setUser(context: Context, dataUser: List<UserModel>) {
        this.dataUser.clear()
        this.dataUser.addAll(dataUser)
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemUserBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_user, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dataUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }


    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserModel) {
            binding.user = user

            binding.TextName.text = "${user.firstName} ${user.lastName}"
            Picasso.get().load(user.picture).into(binding.imgUser)

            binding.textDelete.setOnClickListener {
                showDeleteConfirmationDialog(user)

            }

        }
        private fun showDeleteConfirmationDialog(user: UserModel) {
            AlertDialog.Builder(context)
                .setTitle("Eliminar usuario")
                .setMessage("¿Estás seguro de que deseas eliminar a ${user.firstName} ${user.lastName}")
                .setPositiveButton("Sí") { dialog, which ->
                    onUserClickListener.onUserClicked(user.id)
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

    }

    interface OnUserClickListener {
        fun onUserClicked(id: String)
    }
}