package peterbach1997.example.flickrbrowser

import java.io.Serializable

class Photo(
     val title: String,
     val author: String,
     val authorId: String,
     val link: String,
     val tags: String,
     val image: String
):Serializable  {
    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')"
    }
}