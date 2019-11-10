package es.iessaladillo.pedrojoya.profile.ui.presenter

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.profile.data.local.Database
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar
import es.iessaladillo.pedrojoya.profile.ui.avatar.AvatarActivity
import es.iessaladillo.pedrojoya.profile.ui.main.ProfileActivity

class ProfileViewModel: ViewModel() {

    //La actividad ProfileActivity puede destruirse y crearse de nuevo
    //El objeto viewModel también se crearía de nuevo, borrando los datos que haya guardados
    //Lo evitamos poniendo los datos en un objeto compañero, para que estén a nivel de clase ("estáticos")
    companion object {
        var _avatar = Database.queryDefaultAvatar()
        var _name = ""
        var _email = ""
        var _phone = ""
        var _address = ""
        var _web = ""
    }

    // -- GETTERS Y SETTERS --
    fun saveAvatar(avatar: Avatar) {
        _avatar = avatar
    }

    fun saveName(nameEntered: String) {
        _name = nameEntered
    }

    fun saveEmail(emailEntered: String) {
        _email = emailEntered
    }

    fun savePhone(phoneEntered: String) {
        _phone = phoneEntered
    }

    fun saveAddress(addressEntered: String) {
        _address = addressEntered
    }

    fun saveWeb(webEntered: String) {
        _web = webEntered
    }

    fun getAvatar(): Avatar {
        return _avatar
    }

    fun getName(): String {
        return _name
    }
    fun getEmail(): String {
        return _email
    }
    fun getPhone(): String {
        return _phone
    }
    fun getAddress(): String {
        return _address
    }
    fun getWeb(): String {
        return _web
    }


}