package com.coresystems.codelab.util

import android.app.Activity
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.View
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView

const val ACTION_SHOW_ALL_DISCOVERY = 1
const val FAB_CREATE_MEMOS_DISCOVERY = 2
const val SAVE_CREATED_MEMO_DISCOVERY = 3

class DiscoveryFeature(activity: Activity, val preferencesManager: ISharedPreferencesManager, features: List<DiscoveryFeatureView>) {

    init {
        //Create a discovery features that have to be displayed
        val targets = createTargets(features)

        //Create list of functions that have to be execudet on specific feature
        val lambdaFunctions = lambdasFunctionSparseArray(features)

        if (targets.size > 1) {
            TapTargetSequence(activity).targets(targets).listener(object : TapTargetSequence.Listener {
                override fun onSequenceCanceled(lastTarget: TapTarget?) {
                }

                override fun onSequenceFinish() {

                }

                override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {
                    lambdaFunctions[lastTarget?.id() ?: -1].function.invoke()
                    preferencesManager.setValue(lastTarget!!.id().toString(), true)
                }
            }).start()
        } else if (targets.isNotEmpty()) {
            TapTargetView.showFor(activity, targets.first(), object : TapTargetView.Listener() {
                override fun onTargetClick(view: TapTargetView?) {
                    super.onTargetClick(view)
                    lambdaFunctions[targets.first().id()].function.invoke()
                    preferencesManager.setValue(targets.first().id().toString(), true)
                }

                override fun onOuterCircleClick(view: TapTargetView?) {
                    if (lambdaFunctions[targets.first().id()].cancelable) {
                        preferencesManager.setValue(targets.first().id().toString(), true)
                        view?.dismiss(false)
                    }
                }

                override fun onTargetCancel(view: TapTargetView?) {
                    if (lambdaFunctions[targets[0].id()].cancelable) {
                        preferencesManager.setValue(targets.first().id().toString(), true)
                        view?.dismiss(false)
                    }
                }

            })
        }
    }

    private fun lambdasFunctionSparseArray(features: List<DiscoveryFeatureView>): SparseArray<DiscoveryFeatureFunctionCancelable> {
        val lambdaFunctions = SparseArray<DiscoveryFeatureFunctionCancelable>()
        features.forEach {
            lambdaFunctions.put(it.discoveryFeatureFunction.functionId,
                    DiscoveryFeatureFunctionCancelable(it.discoveryFeatureFunction.function, it.discoveryFeatureFunction.functionId, it.cancelable))
        }
        return lambdaFunctions
    }

    private fun createTargets(features: List<DiscoveryFeatureView>): MutableList<TapTarget> {
        val targets = mutableListOf<TapTarget>()
        features.forEach {
            if (!preferencesManager.getValue(it.discoveryFeatureFunction.functionId.toString())) {
                targets.add(if (it is DiscoveryFeatureMenuItem) {
                    TapTarget.forToolbarMenuItem(it.view as Toolbar, it.menuId, it.title,
                            it.description)
                            .cancelable(it.cancelable)
                            .id(it.discoveryFeatureFunction.functionId)

                } else {
                    TapTarget.forView(it.view, it.title, it.description)
                            .transparentTarget(true)
                            .cancelable(it.cancelable)
                            .id(it.discoveryFeatureFunction.functionId)
                })
            }
        }
        return targets
    }
}


open class DiscoveryFeatureView(val view: View, val title: String,
                                val description: String,
                                val cancelable: Boolean = false,
                                val discoveryFeatureFunction: DiscoveryFeatureFunction)

class DiscoveryFeatureMenuItem(view: Toolbar,
                               val menuId: Int,
                               title: String,
                               description: String,
                               cancelable: Boolean = false,
                               discoveryFeatureFunction: DiscoveryFeatureFunction)
    : DiscoveryFeatureView(view, title, description, cancelable, discoveryFeatureFunction)

open class DiscoveryFeatureFunction(val function: () -> Unit, val functionId: Int)

private class DiscoveryFeatureFunctionCancelable(function: () -> Unit, functionId: Int, val cancelable: Boolean)
    : DiscoveryFeatureFunction(function, functionId)