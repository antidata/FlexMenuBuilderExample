package com.flexmenu.snippet

import net.liftweb._
import http._
import http.S
import sitemap._
import xml._

object MyMenu extends FlexMenu
{
  override def expandAll  = false
  override def linkToSelf = true

  /**
   * Set the class attribute if the node is active
   * @param node the xml element to set active or not
   * @param active indicates if the element must be "active"
   * @return the xml element with the attribute to show as active
   */
  private def setActive(node: Elem, active : Boolean) : Elem = {
    if (active)
      node % S.mapToAttrs(Map("class" -> "active"))
    else
      node
  }

  override def updateForPath(nodes: Elem, path: Boolean): Elem = setActive(nodes, path)

  override def updateForCurrent(nodes: Elem, current: Boolean): Elem = setActive(nodes, current)

  override def renderOuterTag(inner: NodeSeq, top: Boolean): NodeSeq = {
    if (top)
      <ul class="nav nav-pills">{inner}</ul>
    else
      <div class="subnav subnav-fixed">
        <ul class="nav nav-pills">{inner}</ul>
      </div>
  }

  override def emptyPlaceholder: NodeSeq = <li class="vertical-divider"></li>

  override def expandAny = true
}

trait FlexMenu extends FlexMenuBuilder with DispatchSnippet {
  def dispatch: DispatchIt = overridenDispatch orElse net.liftweb.builtin.snippet.Menu.dispatch

  def overridenDispatch: DispatchIt = {
    case "builder" => ignore => render
  }
}
