import UIKit
import React

@objc(IsMultiWindow)
class IsMultiWindow: RCTEventEmitter {
  public static var emitter: RCTEventEmitter!
  
  override init() {
    super.init()
    IsMultiWindow.emitter = self
    addObserver()
  }
  
  override func supportedEvents() -> [String]! {
    return ["onMultiWindowModeChanged"]
  }
  
  @objc func addObserver() {
    NotificationCenter.default.addObserver(self,
                                           selector: #selector(didRCTWindowFrameDidChange),
                                           name: NSNotification.Name.RCTWindowFrameDidChange,
                                           object: nil)
  }
  
  @objc func removeObserver() {
    NotificationCenter.default.removeObserver(self,
                                              name: NSNotification.Name.RCTWindowFrameDidChange,
                                              object: nil)
  }
  
  @objc func didRCTWindowFrameDidChange(notification: Notification) {
    if let screen = notification.object as? UIApplicationDelegate {
      let window = (screen.window) as? UIWindow
      let updatedBounds = window?.bounds
      let fullScreenSize = UIScreen.main.bounds
      
      IsMultiWindow.emitter.sendEvent(
        withName: "onMultiWindowModeChanged",
        body: !(updatedBounds!.width == fullScreenSize.width && updatedBounds!.height == fullScreenSize.height)
      )
    }
  }
  
  @objc(isMultiWindowMode:rejecter:)
  func isMultiWindowMode(_ resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) {
    DispatchQueue.main.async {
      let fullScreenSize = UIScreen.main.bounds
      guard let keyWindow = UIApplication.shared.connectedScenes
        .filter({$0.activationState == .foregroundActive})
        .compactMap({$0 as? UIWindowScene})
        .first?.windows
        .filter({$0.isKeyWindow}).first else {
        resolve(false)
        return
      }
      
      resolve(!(keyWindow.bounds.width == fullScreenSize.width && keyWindow.bounds.height == fullScreenSize.height))
    }
  }
}
