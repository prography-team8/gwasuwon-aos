package com.prography.ui

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Created by MyeongKi.
 */
sealed interface GwasuwonTypography {
    val textStyle: TextStyle

    data object Display1Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 56.sp,
            lineHeight = 72.sp,
            letterSpacing = (-0.0319).em
        )
    }

    data object Display1Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 56.sp,
            lineHeight = 72.sp,
            letterSpacing = (-0.0319).em
        )
    }

    data object Display1Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 56.sp,
            lineHeight = 72.sp,
            letterSpacing = (-0.0319).em
        )
    }

    data object Display2Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            lineHeight = 52.sp,
            letterSpacing = (-0.0282).em
        )
    }

    data object Display2Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 40.sp,
            lineHeight = 52.sp,
            letterSpacing = (-0.0282).em
        )
    }

    data object Display2Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 40.sp,
            lineHeight = 52.sp,
            letterSpacing = (-0.0282).em
        )
    }

    data object Title1Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 48.sp,
            letterSpacing = (-0.027).em
        )
    }

    data object Title1Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 36.sp,
            lineHeight = 48.sp,
            letterSpacing = (-0.027).em
        )
    }

    data object Title1Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 48.sp,
            letterSpacing = (-0.027).em
        )
    }

    data object Title2Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 38.sp,
            letterSpacing = (-0.0236).em
        )
    }

    data object Title2Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp,
            lineHeight = 38.sp,
            letterSpacing = (-0.0236).em
        )
    }

    data object Title2Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 38.sp,
            letterSpacing = (-0.0236).em
        )
    }

    data object Title3Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.023).em
        )
    }

    data object Title3Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.023).em
        )
    }

    data object Title3Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = (-0.023).em
        )
    }

    data object Heading1Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.0194).em
        )
    }

    data object Heading1Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.0194).em
        )
    }

    data object Heading1Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.0194).em
        )
    }

    data object Heading2Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.012).em
        )
    }

    data object Heading2Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.012).em
        )
    }

    data object Heading2Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.012).em
        )
    }

    data object Headline1Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = (-0.002).em
        )
    }

    data object Headline1Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = (-0.002).em
        )
    }

    data object Headline1Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = (-0.002).em
        )
    }

    data object Headline2Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.em
        )
    }

    data object Headline2Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.em
        )
    }

    data object Headline2Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.em
        )
    }

    data object Body1NormalBold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.0057.em
        )
    }

    data object Body1NormalMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.0057.em
        )
    }

    data object Body1NormalRegular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.0057.em
        )
    }

    data object Body1ReadingBold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.0057.em
        )
    }

    data object Body1ReadingMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.0057.em
        )
    }

    data object Body1ReadingRegular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.0057.em
        )
    }

    data object Body2NormalBold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.0096.em
        )
    }

    data object Body2NormalMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.0096.em
        )
    }

    data object Body2NormalRegular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.0096.em
        )
    }

    data object Body2ReadingBold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.0096.em
        )
    }

    data object Body2ReadingMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.0096.em
        )
    }

    data object Body2ReadingRegular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.0096.em
        )
    }

    data object Label1NormalBold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.0145.em
        )
    }

    data object Label1NormalMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.0145.em
        )
    }

    data object Label1NormalRegular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.0145.em
        )
    }

    data object Label1ReadingBold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.0145.em
        )
    }

    data object Label1ReadingMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.0145.em
        )
    }

    data object Label1ReadingRegular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.0145.em
        )
    }

    data object Label2Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.0194.em
        )
    }

    data object Label2Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.0194.em
        )
    }

    data object Label2Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.0194.em
        )
    }

    data object Caption1Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.0252.em
        )
    }

    data object Caption1Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.0252.em
        )
    }

    data object Caption1Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.0252.em
        )
    }

    data object Caption2Bold : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.0311.em
        )
    }

    data object Caption2Medium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.0311.em
        )
    }

    data object Caption2Regular : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.0311.em
        )
    }
    data object BrandLarge : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 56.sp,
            letterSpacing = 0.0311.em
        )
    }

    data object BrandMedium : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 0.0311.em
        )
    }

    data object BrandSmall : GwasuwonTypography {
        override val textStyle: TextStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            letterSpacing = 0.0311.em
        )
    }
}