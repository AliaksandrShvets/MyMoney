package com.goodsoft.mymoney.widgets.funds

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.goodsoft.mymoney.R
import com.goodsoft.mymoney.util.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class FoundsView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private var textTitleAppearance: Int = 0
    private var textTotalAmountAppearance: Int = 0
    private var textAmountAppearance: Int = 0
    private var chipAppearance: Int = 0
    private var textTitleColor: Int = 0
    private var textColor: Int = 0
    private var dividerColor: Int = 0
    private var analyticsRowSelectedColor: Int = 0
    private var analyticsRowUnselectedColor: Int = 0
    private var totalAmountTitleString: String? = null
    private var amountTitleString: String? = null

    private val chipGroup = ChipGroup(context)
    private val diagram = FoundsDiagram(context)
    private lateinit var totalIncomeText: TextView
    private lateinit var amountTitle: TextView
    private lateinit var amountText: TextView

    private var founds: FoundsData? = null

    init {
        layoutParams = ViewGroup.LayoutParams(context, attrs).apply {
            setPadding(16.px, 12.px, 16.px, 16.px)
        }

        context.obtainStyledAttributes(attrs, R.styleable.FoundsView).apply {
            for (i in 0 until indexCount) {
                when (val attr = getIndex(i)) {
                    R.styleable.FoundsView_fv_textTitleAppearance -> textTitleAppearance = getResourceId(attr, 0)
                    R.styleable.FoundsView_fv_textTotalAmountAppearance -> textTotalAmountAppearance = getResourceId(attr, 0)
                    R.styleable.FoundsView_fv_textAmountAppearance -> textAmountAppearance = getResourceId(attr, 0)
                    R.styleable.FoundsView_fv_chipAppearance -> chipAppearance = getResourceId(attr, 0)
                    R.styleable.FoundsView_fv_textTitleColor -> textTitleColor = getColor(attr, 0)
                    R.styleable.FoundsView_fv_textColor -> textColor = getColor(attr, 0)
                    R.styleable.FoundsView_fv_analyticsRowSelectedColor -> analyticsRowSelectedColor = getColor(attr, 0)
                    R.styleable.FoundsView_fv_analyticsRowUnselectedColor -> analyticsRowUnselectedColor = getColor(attr, 0)
                    R.styleable.FoundsView_fv_dividerColor -> dividerColor = getColor(attr, 0)
                    R.styleable.FoundsView_fv_totalAmountTitle -> totalAmountTitleString = getString(attr)
                    R.styleable.FoundsView_fv_amountTitle -> amountTitleString = getString(attr)
                }
            }
            recycle()
        }

        addViews()
    }

    private fun addViews() {

        //adding views
        val totalAmountTitle = TextView(ContextThemeWrapper(context, textTitleAppearance))
        totalAmountTitle.id = ViewIdGenerator.generate()
        totalAmountTitle.text = totalAmountTitleString
        totalAmountTitle.setTextColor(textTitleColor)
        addView(totalAmountTitle, ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT))

        totalIncomeText = TextView(ContextThemeWrapper(context, textAmountAppearance))
        totalIncomeText.id = ViewIdGenerator.generate()
        totalIncomeText.setTextColor(textColor)
        addView(totalIncomeText, ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT))

        val divider = View(context)
        divider.id = ViewIdGenerator.generate()
        divider.setBackgroundColor(dividerColor)
        addView(divider, ViewGroup.LayoutParams(0, 1.px))

        amountTitle = TextView(ContextThemeWrapper(context, textTitleAppearance))
        amountTitle.id = ViewIdGenerator.generate()
        amountTitle.setTextColor(textTitleColor)
        addView(amountTitle, ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT))

        amountText = TextView(ContextThemeWrapper(context, textAmountAppearance))
        amountText.id = ViewIdGenerator.generate()
        amountText.setTextColor(textColor)
        addView(amountText, ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT))

        diagram.id = ViewIdGenerator.generate()
        diagram.setSelectedColor(analyticsRowSelectedColor)
        diagram.setUnselectedColor(analyticsRowUnselectedColor)
        addView(diagram, ViewGroup.LayoutParams(104.px, 0))

        val diagramBottomGuidLine = ImageView(context)
        diagramBottomGuidLine.id = ViewIdGenerator.generate()
        diagramBottomGuidLine.baselineAlignBottom = true
        addView(diagramBottomGuidLine, ViewGroup.LayoutParams(0, 1))

        chipGroup.id = ViewIdGenerator.generate()
        chipGroup.chipSpacingHorizontal = 0
        chipGroup.isSingleSelection = true
        addView(chipGroup, ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT))

        //setting constraints
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.connect(totalAmountTitle.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        constraintSet.connect(totalAmountTitle.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(totalAmountTitle.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(totalAmountTitle.id, ConstraintSet.BOTTOM, totalIncomeText.id, ConstraintSet.TOP, 0)

        constraintSet.connect(totalIncomeText.id, ConstraintSet.TOP, totalAmountTitle.id, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(totalIncomeText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(totalIncomeText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(totalIncomeText.id, ConstraintSet.BOTTOM, divider.id, ConstraintSet.TOP, 0)

        constraintSet.connect(divider.id, ConstraintSet.TOP, totalIncomeText.id, ConstraintSet.BOTTOM, 9.px)
        constraintSet.connect(divider.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(divider.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(divider.id, ConstraintSet.BOTTOM, diagram.id, ConstraintSet.TOP, 0)

        constraintSet.connect(amountTitle.id, ConstraintSet.TOP, divider.id, ConstraintSet.BOTTOM, 16.px)
        constraintSet.connect(amountTitle.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(amountTitle.id, ConstraintSet.RIGHT, diagram.id, ConstraintSet.LEFT, 0)
        constraintSet.connect(amountTitle.id, ConstraintSet.BOTTOM, amountText.id, ConstraintSet.TOP, 0)

        constraintSet.connect(amountText.id, ConstraintSet.TOP, amountTitle.id, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(amountText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(amountText.id, ConstraintSet.RIGHT, diagram.id, ConstraintSet.LEFT, 0)
        constraintSet.connect(amountText.id, ConstraintSet.BOTTOM, chipGroup.id, ConstraintSet.TOP, 0)

        constraintSet.connect(diagramBottomGuidLine.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(diagramBottomGuidLine.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(diagramBottomGuidLine.id, ConstraintSet.BASELINE, amountText.id, ConstraintSet.BASELINE, 0)

        constraintSet.connect(diagram.id, ConstraintSet.TOP, divider.id, ConstraintSet.BOTTOM, 8.px)
        constraintSet.connect(diagram.id, ConstraintSet.LEFT, amountTitle.id, ConstraintSet.RIGHT, 0)
        constraintSet.connect(diagram.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(diagram.id, ConstraintSet.BOTTOM, diagramBottomGuidLine.id, ConstraintSet.TOP, 0)

        constraintSet.connect(chipGroup.id, ConstraintSet.TOP, amountText.id, ConstraintSet.BOTTOM, 16.px)
        constraintSet.connect(chipGroup.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraintSet.connect(chipGroup.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        constraintSet.connect(chipGroup.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

        constraintSet.applyTo(this)
    }

    private fun initAnalytics(founds: FoundsData) {
        totalIncomeText.text = "+${context.formatAmount(founds.totalIncome, founds.currency)}; -${context.formatAmount(founds.totalOutcome, founds.currency)}"
        chipGroup.removeAllViews()
        chipGroup.post {
            for (i in founds.daysAmountSpentList.indices) {
                val cli = LayoutInflater.from(context).inflate(R.layout.single_chip_layout, chipGroup, false) as Chip
                cli.id = ViewIdGenerator.generate()
                cli.setOnCheckedChangeListener { buttonView, isChecked ->
                    buttonView.isClickable = !isChecked
                    if (isChecked) {
                        diagram.drawDiagram(founds.daysAmountSpentList, i)
                        amountText.text = context.formatAmount(founds.daysAmountSpentList[i].amount, founds.daysAmountSpentList[i].currency)
                        amountTitleString?.let {
                            amountTitle.text = String.format(it, fullDateFormat.format(founds.daysAmountSpentList[i].date).toString())
                        }
                    }
                }
                cli.textAlignment = View.TEXT_ALIGNMENT_CENTER
                cli.setTextAppearance(chipAppearance)
                cli.text = weekFormat.format(founds.daysAmountSpentList[i].date)
                cli.chipStartPadding = 0f + 2.px
                cli.chipEndPadding = 0f + 4.px
                cli.isCheckable = true
                cli.checkedIcon = null
                cli.isChecked = true
                chipGroup.addView(cli, ViewGroup.LayoutParams(chipGroup.width / 7, ViewGroup.LayoutParams.WRAP_CONTENT))
            }
        }
    }

    fun setData(founds: FoundsData?) {
        this.founds = founds
        this.founds?.let { initAnalytics(it) }
    }
}