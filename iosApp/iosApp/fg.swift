import SwiftUI
import AVKit
import AVFoundation
import shared


struct VideoPlayerUIView: UIViewRepresentable {
    let url: URL

    func makeUIView(context: Context) -> UIView {
        return PlayerView(frame: .zero, url: url)
    }

    func updateUIView(_ uiView: UIView, context: Context) {}

    class PlayerView: UIView {
        private var player: AVPlayer?

        init(frame: CGRect, url: URL) {
            super.init(frame: frame)
            setupPlayer(url: url)
        }

        required init?(coder: NSCoder) {
            fatalError("init(coder:) has not been implemented")
        }

        private func setupPlayer(_ url: URL) {
            player = AVPlayer(url: url)
            let playerLayer = AVPlayerLayer(player: player)
            playerLayer.videoGravity = .resizeAspect
            layer.addSublayer(playerLayer)
            playerLayer.frame = bounds
            player?.play()
        }

        override func layoutSubviews() {
            super.layoutSubviews()
            layer.sublayers?.first?.frame = bounds
        }
    }
}


@objc class VideoPlayerHost: NSObject {
    @objc static func createVideoPlayer(urlString: String) -> UIViewController {
        guard let url = URL(string: urlString) else {
            return UIViewController()
        }
        let controller = UIHostingController(rootView: VideoPlayerUIView(url: url))
        return controller
    }
}
