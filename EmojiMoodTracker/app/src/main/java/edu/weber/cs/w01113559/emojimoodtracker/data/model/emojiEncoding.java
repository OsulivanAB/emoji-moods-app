package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.weber.cs.w01113559.emojimoodtracker.R;

public abstract class emojiEncoding {

    public static String decodeEmoji(Drawable emoji, Context context){
        if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f600)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f600";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f624)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f624";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f923)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f923";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f601)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f601";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f625)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f625";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f924)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f924";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f602)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f602";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f626)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f626";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f925)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f925";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f603)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f603";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f627)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f627";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f927)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f927";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f604)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f604";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f628)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f628";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f928)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f928";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f605)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f605";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f629)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f629";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f929)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f929";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f606)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f606";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62a)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62a";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f92a)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f92a";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f607)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f607";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62b)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62b";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f92c)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f92c";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f608)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f608";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62c)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62c";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f92d)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f92d";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f609)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f609";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62d)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62d";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f92e)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f92e";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f60a)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f60a";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62e)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62e";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f92f)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f92f";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f60b)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f60b";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62e_200d_1f4a8)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62e_200d_1f4a8";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f970)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f970";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f60c)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f60c";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f62f)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f62f";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f971)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f971";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f60d)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f60d";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f630)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f630";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f972)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f972";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f60e)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f60e";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f631)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f631";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f973)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f973";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f60f)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f60f";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f632)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f632";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f974)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f974";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f610)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f610";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f633)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f633";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f975)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f975";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f611)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f611";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f634)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f634";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f976)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f976";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f612)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f612";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f635)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f635";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f978)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f978";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f613)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f613";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f635_200d_1f4ab)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f635_200d_1f4ab";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f979)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f979";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f614)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f614";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f636)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f636";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f97a)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f97a";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f615)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f615";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f636_200d_1f32b)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f636_200d_1f32b";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1fae0)).getConstantState().equals(emoji.getConstantState())) {
            return "u1fae0";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f616)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f616";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f637)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f637";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1fae1)).getConstantState().equals(emoji.getConstantState())) {
            return "u1fae1";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f617)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f617";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f641)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f641";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1fae2)).getConstantState().equals(emoji.getConstantState())) {
            return "u1fae2";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f618)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f618";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f642)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f642";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1fae3)).getConstantState().equals(emoji.getConstantState())) {
            return "u1fae3";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f619)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f619";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f643)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f643";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1fae4)).getConstantState().equals(emoji.getConstantState())) {
            return "u1fae4";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f61a)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f61a";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f644)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f644";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u2639)).getConstantState().equals(emoji.getConstantState())) {
            return "u2639:";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f61b)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f61b";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f910)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f910";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u263a)).getConstantState().equals(emoji.getConstantState())) {
            return "u263a:";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f61c)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f61c";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f911)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f911";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f61d)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f61d";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f912)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f912";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f61e)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f61e";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f913)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f913";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f61f)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f61f";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f914)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f914";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f620)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f620";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f915)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f915";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f621)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f621";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f917)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f917";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f622)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f622";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f920)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f920";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f623)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f623";
        } else if (Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.emoji_u1f922)).getConstantState().equals(emoji.getConstantState())) {
            return "u1f922";
        }
        throw new IllegalArgumentException("Invalid drawable submitted to create emoji record.");
    }

    public static Drawable encodeEmoji(String decodedEmoji, Context context){
        switch (decodedEmoji) {
            case "u1f600":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f600);
            case "u1f624":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f624);
            case "u1f923":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f923);
            case "u1f601":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f601);
            case "u1f625":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f625);
            case "u1f924":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f924);
            case "u1f602":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f602);
            case "u1f626":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f626);
            case "u1f925":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f925);
            case "u1f603":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f603);
            case "u1f627":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f627);
            case "u1f927":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f927);
            case "u1f604":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f604);
            case "u1f628":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f628);
            case "u1f928":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f928);
            case "u1f605":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f605);
            case "u1f629":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f629);
            case "u1f929":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f929);
            case "u1f606":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f606);
            case "u1f62a":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62a);
            case "u1f92a":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f92a);
            case "u1f607":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f607);
            case "u1f62b":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62b);
            case "u1f92c":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f92c);
            case "u1f608":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f608);
            case "u1f62c":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62c);
            case "u1f92d":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f92d);
            case "u1f609":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f609);
            case "u1f62d":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62d);
            case "u1f92e":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f92e);
            case "u1f60a":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f60a);
            case "u1f62e":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62e);
            case "u1f92f":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f92f);
            case "u1f60b":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f60b);
            case "u1f62e_200d_1f4a8":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62e_200d_1f4a8);
            case "u1f970":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f970);
            case "u1f60c":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f60c);
            case "u1f62f":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f62f);
            case "u1f971":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f971);
            case "u1f60d":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f60d);
            case "u1f630":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f630);
            case "u1f972":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f972);
            case "u1f60e":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f60e);
            case "u1f631":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f631);
            case "u1f973":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f973);
            case "u1f60f":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f60f);
            case "u1f632":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f632);
            case "u1f974":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f974);
            case "u1f610":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f610);
            case "u1f633":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f633);
            case "u1f975":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f975);
            case "u1f611":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f611);
            case "u1f634":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f634);
            case "u1f976":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f976);
            case "u1f612":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f612);
            case "u1f635":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f635);
            case "u1f978":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f978);
            case "u1f613":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f613);
            case "u1f635_200d_1f4ab":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f635_200d_1f4ab);
            case "u1f979":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f979);
            case "u1f614":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f614);
            case "u1f636":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f636);
            case "u1f97a":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f97a);
            case "u1f615":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f615);
            case "u1f636_200d_1f32b":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f636_200d_1f32b);
            case "u1fae0":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1fae0);
            case "u1f616":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f616);
            case "u1f637":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f637);
            case "u1fae1":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1fae1);
            case "u1f617":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f617);
            case "u1f641":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f641);
            case "u1fae2":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1fae2);
            case "u1f618":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f618);
            case "u1f642":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f642);
            case "u1fae3":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1fae3);
            case "u1f619":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f619);
            case "u1f643":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f643);
            case "u1fae4":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1fae4);
            case "u1f61a":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f61a);
            case "u1f644":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f644);
            case "u2639:":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u2639);
            case "u1f61b":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f61b);
            case "u1f910":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f910);
            case "u263a:":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u263a);
            case "u1f61c":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f61c);
            case "u1f911":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f911);
            case "u1f61d":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f61d);
            case "u1f912":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f912);
            case "u1f61e":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f61e);
            case "u1f913":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f913);
            case "u1f61f":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f61f);
            case "u1f914":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f914);
            case "u1f620":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f620);
            case "u1f915":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f915);
            case "u1f621":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f621);
            case "u1f917":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f917);
            case "u1f622":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f622);
            case "u1f920":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f920);
            case "u1f623":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f623);
            case "u1f922":
                return ContextCompat.getDrawable(context, R.drawable.emoji_u1f922);
            default:
                throw new IllegalArgumentException("Invalid drawable submitted to create emoji record.");
        }
    }

    @NonNull
    public static List<String> getDecodedEmojiList(@NonNull Context context, @NonNull List<Drawable> emojis){
        List<String> sEmojiList = new ArrayList<>();
        for (Drawable emoji : emojis) {
            sEmojiList.add(decodeEmoji(emoji, context));
        }
        return sEmojiList;
    }

    @NonNull
    public static List<Drawable> getEncodedEmojiList(@NonNull Context context, @NonNull List<String> emojis){
        List<Drawable> dEmojiList = new ArrayList<>();
        for (String emoji : emojis) {
            dEmojiList.add(encodeEmoji(emoji, context));
        }
        return dEmojiList;
    }
}
