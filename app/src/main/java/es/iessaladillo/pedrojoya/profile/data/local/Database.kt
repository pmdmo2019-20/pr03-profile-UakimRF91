package es.iessaladillo.pedrojoya.profile.data.local

import es.iessaladillo.pedrojoya.profile.R
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar

object Database {

    private val avatars = listOf(
        Avatar(1, R.drawable.pikachu, "Pikachu"),
        Avatar(2, R.drawable.cubone, "Cubone"),
        Avatar(3, R.drawable.gyarados, "Gyarados"),
        Avatar(4, R.drawable.bulbasur, "Bulbasur"),
        Avatar(5, R.drawable.feebas, "Feebas"),
        Avatar(6, R.drawable.jigglypuff, "Jigglypuff"),
        Avatar(7, R.drawable.chikorita, "Chikorita"),
        Avatar(8, R.drawable.giratina, "Giratina"),
        Avatar(9, R.drawable.pachirisu, "Pachirisu")
    )

    fun queryDefaultAvatar(): Avatar = avatars[0]

    fun queryAllAvatars(): List<Avatar> = ArrayList(avatars)

}
