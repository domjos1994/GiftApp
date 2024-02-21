package de.domjos.gift_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import de.domjos.gift_app.R
import de.domjos.gift_app.adapters.CustomArrayAdapter
import de.domjos.gift_app.database.BibleService
import de.domjos.gift_app.databinding.FragmentBibleBinding
import de.domjos.gift_app.model.BibleSummary
import de.domjos.gift_app.model.Book
import de.domjos.gift_app.model.Chapter
import de.domjos.gift_app.model.ChapterSummary
import de.domjos.gift_app.model.Verse
import de.domjos.gift_app.services.TaskRunner


class BibleFragment : Fragment() {

    private var _binding: FragmentBibleBinding? = null
    private val binding get() = _binding!!
    private var service: BibleService? = null
    private lateinit var bibleAdapter: CustomArrayAdapter<BibleSummary>
    private lateinit var bookAdapter: CustomArrayAdapter<Book>
    private lateinit var chapterAdapter: CustomArrayAdapter<ChapterSummary>
    private var chapter: Chapter? = null

    private lateinit var spBibleChoice: Spinner
    private lateinit var spBibleChapters: Spinner
    private lateinit var spBibleBooks: Spinner
    private lateinit var lblBibleContent: WebView
    private lateinit var lblBibleCopyright: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBibleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spBibleChoice = binding.spBibleChoice
        spBibleBooks = binding.spBibleBooks
        spBibleChapters = binding.spBibleChapters

        lblBibleContent = binding.lblBibleContent
        lblBibleCopyright = binding.lblBibleCopyright

        val cmdBibleNext = binding.cmdBibleNext
        val cmdBiblePrevious = binding.cmdBiblePrevious

        val ctx = requireContext()
        service = BibleService(ctx)
        service?.getBibles(object : TaskRunner.Callback<List<BibleSummary>?> {
            override fun onComplete(result: List<BibleSummary>?) {
                try {
                    bibleAdapter = CustomArrayAdapter(ctx, result!!) { item, iv, title, subTitle ->
                        iv.setImageDrawable(AppCompatResources.getDrawable(ctx, R.drawable.baseline_book_24))
                        title.text = item.nameLocal
                        subTitle.text = item.descriptionLocal
                    }
                    spBibleChoice.adapter = bibleAdapter
                } catch (ex: Exception) {
                    print(ex)
                }
            }
        })

        spBibleChoice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                try {
                    val item = bibleAdapter.getItem(position)

                    service?.getBooks(object: TaskRunner.Callback<List<Book>?> {
                        override fun onComplete(result: List<Book>?) {
                            bookAdapter = CustomArrayAdapter(ctx, result!!) { item, iv, title, subTitle ->
                                iv.setImageDrawable(AppCompatResources.getDrawable(ctx, R.drawable.baseline_book_24))
                                title.text = item.name
                                subTitle.text = item.abbreviation
                            }
                            spBibleBooks.adapter = bookAdapter
                        }
                    }, item?.id!!)
                } catch (ex: Exception) {
                    print(ex)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                bookAdapter.clear()
            }
        }

        spBibleBooks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                try {
                    val bibleItem = bibleAdapter.getItem(spBibleChoice.selectedItemPosition)
                    val bookItem = bookAdapter.getItem(position)

                    service?.getChapters(object: TaskRunner.Callback<List<ChapterSummary>?> {
                        override fun onComplete(result: List<ChapterSummary>?) {
                            chapterAdapter = CustomArrayAdapter(ctx, result!!) { item, iv, title, subTitle ->
                                iv.setImageDrawable(AppCompatResources.getDrawable(ctx, R.drawable.gift_item_header))
                                title.text = item.reference
                                subTitle.text = item.number
                            }
                            spBibleChapters.adapter = chapterAdapter
                        }
                    }, bibleItem?.id!!, bookItem?.id!!)
                } catch (ex: Exception) {
                    print(ex)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                chapterAdapter.clear()
            }
        }

        spBibleChapters.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                try {
                    val chapterItem = chapterAdapter.getItem(position)
                    val chapterId = chapterItem?.id

                    setChapter(chapterId!!)
                } catch(ex: Exception) {
                    print(ex)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        cmdBiblePrevious.setOnClickListener {
            try {
                if(chapter != null) {
                    setChapter(chapter?.previous?.id!!)
                }
            } catch(ex: Exception) {
                print(ex)
            }
        }

        cmdBibleNext.setOnClickListener {
            try {
                if(chapter != null) {
                    setChapter(chapter?.next?.id!!)
                }
            } catch(ex: Exception) {
                print(ex)
            }
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
                menu.findItem(R.id.action_impress).isVisible = false
                menu.findItem(R.id.action_reset).isVisible = false

                val item = menu.findItem(R.id.search)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        service?.search(object: TaskRunner.Callback<List<Verse>?> {
                            override fun onComplete(result: List<Verse>?) {
                                var content = ""
                                result?.forEach { item -> content += item.reference + "<br/>" + item.content + "<br/>" + item.copyright + "<br/><hr/>" }

                                lblBibleContent.loadData(content, "text/html", "UTF-8")
                            }
                        }, bibleAdapter.getItem(spBibleChoice.selectedItemPosition)?.id!!, p0!!)
                        return true
                    }
                    override fun onQueryTextChange(p0: String?): Boolean {
                        return true
                    }
                })
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)
    }

    private fun setChapter(id: String) {
        try {
            val bibleItem = bibleAdapter.getItem(spBibleChoice.selectedItemPosition)

            service?.getChapter(object: TaskRunner.Callback<Chapter?> {
                override fun onComplete(result: Chapter?) {
                    chapter = result
                    lblBibleContent.loadData(result?.content!!, "text/html", "UTF-8")
                    lblBibleCopyright.text = result.copyright

                    val bibleSummary = bibleAdapter.getItem(spBibleChoice.selectedItemPosition)!!
                    bibleSummary.relatedDbl = ""
                    val chapterSummary = chapterAdapter.getItem(spBibleChapters.selectedItemPosition)!!
                    val book = bookAdapter.getItem(spBibleBooks.selectedItemPosition)!!
                    val fChapter = chapter!!

                    service?.saveData(
                        object: TaskRunner.Callback<Unit?> {override fun onComplete(result: Unit?) {}},
                        fChapter, chapterSummary, book, bibleSummary
                    )
                }
            }, bibleItem?.id!!, id)
        } catch(ex: Exception) {
            print(ex)
        }
    }

    private fun print(ex: Exception) {
        Toast.makeText(requireContext(), ex.message, Toast.LENGTH_LONG).show()
    }
}