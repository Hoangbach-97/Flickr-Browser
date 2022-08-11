package peterbach1997.example.flickrbrowser

class Photo(
    private val title: String,
    private val author: String,
    private val authorId: String,
    private val link: String,
    private val tags: String,
    private val image: String
)  {
    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')"
    }
}